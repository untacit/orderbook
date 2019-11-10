package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderbookApp;

import com.mycompany.myapp.domain.PurchasedBook;
import com.mycompany.myapp.repository.PurchasedBookRepository;
import com.mycompany.myapp.service.dto.PurchasedBookDTO;
import com.mycompany.myapp.service.mapper.PurchasedBookMapper;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PurchasedBookResource REST controller.
 *
 * @see PurchasedBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderbookApp.class)
public class PurchasedBookResourceIntTest {

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Integer DEFAULT_PRICE = 0;
    private static final Integer UPDATED_PRICE = 1;

    @Autowired
    private PurchasedBookRepository purchasedBookRepository;

    @Autowired
    private PurchasedBookMapper purchasedBookMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPurchasedBookMockMvc;

    private PurchasedBook purchasedBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PurchasedBookResource purchasedBookResource = new PurchasedBookResource(purchasedBookRepository, purchasedBookMapper);
        this.restPurchasedBookMockMvc = MockMvcBuilders.standaloneSetup(purchasedBookResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PurchasedBook createEntity(EntityManager em) {
        PurchasedBook purchasedBook = new PurchasedBook()
            .quantity(DEFAULT_QUANTITY)
            .price(DEFAULT_PRICE);
        return purchasedBook;
    }

    @Before
    public void initTest() {
        purchasedBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createPurchasedBook() throws Exception {
        int databaseSizeBeforeCreate = purchasedBookRepository.findAll().size();

        // Create the PurchasedBook
        PurchasedBookDTO purchasedBookDTO = purchasedBookMapper.toDto(purchasedBook);
        restPurchasedBookMockMvc.perform(post("/api/purchased-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasedBookDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchasedBook in the database
        List<PurchasedBook> purchasedBookList = purchasedBookRepository.findAll();
        assertThat(purchasedBookList).hasSize(databaseSizeBeforeCreate + 1);
        PurchasedBook testPurchasedBook = purchasedBookList.get(purchasedBookList.size() - 1);
        assertThat(testPurchasedBook.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testPurchasedBook.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createPurchasedBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = purchasedBookRepository.findAll().size();

        // Create the PurchasedBook with an existing ID
        purchasedBook.setId(1L);
        PurchasedBookDTO purchasedBookDTO = purchasedBookMapper.toDto(purchasedBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPurchasedBookMockMvc.perform(post("/api/purchased-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasedBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PurchasedBook in the database
        List<PurchasedBook> purchasedBookList = purchasedBookRepository.findAll();
        assertThat(purchasedBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = purchasedBookRepository.findAll().size();
        // set the field null
        purchasedBook.setPrice(null);

        // Create the PurchasedBook, which fails.
        PurchasedBookDTO purchasedBookDTO = purchasedBookMapper.toDto(purchasedBook);

        restPurchasedBookMockMvc.perform(post("/api/purchased-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasedBookDTO)))
            .andExpect(status().isBadRequest());

        List<PurchasedBook> purchasedBookList = purchasedBookRepository.findAll();
        assertThat(purchasedBookList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPurchasedBooks() throws Exception {
        // Initialize the database
        purchasedBookRepository.saveAndFlush(purchasedBook);

        // Get all the purchasedBookList
        restPurchasedBookMockMvc.perform(get("/api/purchased-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(purchasedBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)));
    }

    @Test
    @Transactional
    public void getPurchasedBook() throws Exception {
        // Initialize the database
        purchasedBookRepository.saveAndFlush(purchasedBook);

        // Get the purchasedBook
        restPurchasedBookMockMvc.perform(get("/api/purchased-books/{id}", purchasedBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(purchasedBook.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE));
    }

    @Test
    @Transactional
    public void getNonExistingPurchasedBook() throws Exception {
        // Get the purchasedBook
        restPurchasedBookMockMvc.perform(get("/api/purchased-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePurchasedBook() throws Exception {
        // Initialize the database
        purchasedBookRepository.saveAndFlush(purchasedBook);
        int databaseSizeBeforeUpdate = purchasedBookRepository.findAll().size();

        // Update the purchasedBook
        PurchasedBook updatedPurchasedBook = purchasedBookRepository.findOne(purchasedBook.getId());
        // Disconnect from session so that the updates on updatedPurchasedBook are not directly saved in db
        em.detach(updatedPurchasedBook);
        updatedPurchasedBook
            .quantity(UPDATED_QUANTITY)
            .price(UPDATED_PRICE);
        PurchasedBookDTO purchasedBookDTO = purchasedBookMapper.toDto(updatedPurchasedBook);

        restPurchasedBookMockMvc.perform(put("/api/purchased-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasedBookDTO)))
            .andExpect(status().isOk());

        // Validate the PurchasedBook in the database
        List<PurchasedBook> purchasedBookList = purchasedBookRepository.findAll();
        assertThat(purchasedBookList).hasSize(databaseSizeBeforeUpdate);
        PurchasedBook testPurchasedBook = purchasedBookList.get(purchasedBookList.size() - 1);
        assertThat(testPurchasedBook.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testPurchasedBook.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingPurchasedBook() throws Exception {
        int databaseSizeBeforeUpdate = purchasedBookRepository.findAll().size();

        // Create the PurchasedBook
        PurchasedBookDTO purchasedBookDTO = purchasedBookMapper.toDto(purchasedBook);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPurchasedBookMockMvc.perform(put("/api/purchased-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(purchasedBookDTO)))
            .andExpect(status().isCreated());

        // Validate the PurchasedBook in the database
        List<PurchasedBook> purchasedBookList = purchasedBookRepository.findAll();
        assertThat(purchasedBookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePurchasedBook() throws Exception {
        // Initialize the database
        purchasedBookRepository.saveAndFlush(purchasedBook);
        int databaseSizeBeforeDelete = purchasedBookRepository.findAll().size();

        // Get the purchasedBook
        restPurchasedBookMockMvc.perform(delete("/api/purchased-books/{id}", purchasedBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PurchasedBook> purchasedBookList = purchasedBookRepository.findAll();
        assertThat(purchasedBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasedBook.class);
        PurchasedBook purchasedBook1 = new PurchasedBook();
        purchasedBook1.setId(1L);
        PurchasedBook purchasedBook2 = new PurchasedBook();
        purchasedBook2.setId(purchasedBook1.getId());
        assertThat(purchasedBook1).isEqualTo(purchasedBook2);
        purchasedBook2.setId(2L);
        assertThat(purchasedBook1).isNotEqualTo(purchasedBook2);
        purchasedBook1.setId(null);
        assertThat(purchasedBook1).isNotEqualTo(purchasedBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PurchasedBookDTO.class);
        PurchasedBookDTO purchasedBookDTO1 = new PurchasedBookDTO();
        purchasedBookDTO1.setId(1L);
        PurchasedBookDTO purchasedBookDTO2 = new PurchasedBookDTO();
        assertThat(purchasedBookDTO1).isNotEqualTo(purchasedBookDTO2);
        purchasedBookDTO2.setId(purchasedBookDTO1.getId());
        assertThat(purchasedBookDTO1).isEqualTo(purchasedBookDTO2);
        purchasedBookDTO2.setId(2L);
        assertThat(purchasedBookDTO1).isNotEqualTo(purchasedBookDTO2);
        purchasedBookDTO1.setId(null);
        assertThat(purchasedBookDTO1).isNotEqualTo(purchasedBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(purchasedBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(purchasedBookMapper.fromId(null)).isNull();
    }
}
