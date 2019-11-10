package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderbookApp;

import com.mycompany.myapp.domain.OrderedBook;
import com.mycompany.myapp.repository.OrderedBookRepository;
import com.mycompany.myapp.service.dto.OrderedBookDTO;
import com.mycompany.myapp.service.mapper.OrderedBookMapper;
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
import java.math.BigDecimal;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderedBookResource REST controller.
 *
 * @see OrderedBookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderbookApp.class)
public class OrderedBookResourceIntTest {

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    @Autowired
    private OrderedBookRepository orderedBookRepository;

    @Autowired
    private OrderedBookMapper orderedBookMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderedBookMockMvc;

    private OrderedBook orderedBook;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderedBookResource orderedBookResource = new OrderedBookResource(orderedBookRepository, orderedBookMapper);
        this.restOrderedBookMockMvc = MockMvcBuilders.standaloneSetup(orderedBookResource)
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
    public static OrderedBook createEntity(EntityManager em) {
        OrderedBook orderedBook = new OrderedBook()
            .price(DEFAULT_PRICE);
        return orderedBook;
    }

    @Before
    public void initTest() {
        orderedBook = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderedBook() throws Exception {
        int databaseSizeBeforeCreate = orderedBookRepository.findAll().size();

        // Create the OrderedBook
        OrderedBookDTO orderedBookDTO = orderedBookMapper.toDto(orderedBook);
        restOrderedBookMockMvc.perform(post("/api/ordered-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderedBookDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderedBook in the database
        List<OrderedBook> orderedBookList = orderedBookRepository.findAll();
        assertThat(orderedBookList).hasSize(databaseSizeBeforeCreate + 1);
        OrderedBook testOrderedBook = orderedBookList.get(orderedBookList.size() - 1);
        assertThat(testOrderedBook.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createOrderedBookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderedBookRepository.findAll().size();

        // Create the OrderedBook with an existing ID
        orderedBook.setId(1L);
        OrderedBookDTO orderedBookDTO = orderedBookMapper.toDto(orderedBook);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderedBookMockMvc.perform(post("/api/ordered-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderedBookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderedBook in the database
        List<OrderedBook> orderedBookList = orderedBookRepository.findAll();
        assertThat(orderedBookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderedBooks() throws Exception {
        // Initialize the database
        orderedBookRepository.saveAndFlush(orderedBook);

        // Get all the orderedBookList
        restOrderedBookMockMvc.perform(get("/api/ordered-books?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderedBook.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())));
    }

    @Test
    @Transactional
    public void getOrderedBook() throws Exception {
        // Initialize the database
        orderedBookRepository.saveAndFlush(orderedBook);

        // Get the orderedBook
        restOrderedBookMockMvc.perform(get("/api/ordered-books/{id}", orderedBook.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderedBook.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderedBook() throws Exception {
        // Get the orderedBook
        restOrderedBookMockMvc.perform(get("/api/ordered-books/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderedBook() throws Exception {
        // Initialize the database
        orderedBookRepository.saveAndFlush(orderedBook);
        int databaseSizeBeforeUpdate = orderedBookRepository.findAll().size();

        // Update the orderedBook
        OrderedBook updatedOrderedBook = orderedBookRepository.findOne(orderedBook.getId());
        // Disconnect from session so that the updates on updatedOrderedBook are not directly saved in db
        em.detach(updatedOrderedBook);
        updatedOrderedBook
            .price(UPDATED_PRICE);
        OrderedBookDTO orderedBookDTO = orderedBookMapper.toDto(updatedOrderedBook);

        restOrderedBookMockMvc.perform(put("/api/ordered-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderedBookDTO)))
            .andExpect(status().isOk());

        // Validate the OrderedBook in the database
        List<OrderedBook> orderedBookList = orderedBookRepository.findAll();
        assertThat(orderedBookList).hasSize(databaseSizeBeforeUpdate);
        OrderedBook testOrderedBook = orderedBookList.get(orderedBookList.size() - 1);
        assertThat(testOrderedBook.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderedBook() throws Exception {
        int databaseSizeBeforeUpdate = orderedBookRepository.findAll().size();

        // Create the OrderedBook
        OrderedBookDTO orderedBookDTO = orderedBookMapper.toDto(orderedBook);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderedBookMockMvc.perform(put("/api/ordered-books")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderedBookDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderedBook in the database
        List<OrderedBook> orderedBookList = orderedBookRepository.findAll();
        assertThat(orderedBookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderedBook() throws Exception {
        // Initialize the database
        orderedBookRepository.saveAndFlush(orderedBook);
        int databaseSizeBeforeDelete = orderedBookRepository.findAll().size();

        // Get the orderedBook
        restOrderedBookMockMvc.perform(delete("/api/ordered-books/{id}", orderedBook.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderedBook> orderedBookList = orderedBookRepository.findAll();
        assertThat(orderedBookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderedBook.class);
        OrderedBook orderedBook1 = new OrderedBook();
        orderedBook1.setId(1L);
        OrderedBook orderedBook2 = new OrderedBook();
        orderedBook2.setId(orderedBook1.getId());
        assertThat(orderedBook1).isEqualTo(orderedBook2);
        orderedBook2.setId(2L);
        assertThat(orderedBook1).isNotEqualTo(orderedBook2);
        orderedBook1.setId(null);
        assertThat(orderedBook1).isNotEqualTo(orderedBook2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderedBookDTO.class);
        OrderedBookDTO orderedBookDTO1 = new OrderedBookDTO();
        orderedBookDTO1.setId(1L);
        OrderedBookDTO orderedBookDTO2 = new OrderedBookDTO();
        assertThat(orderedBookDTO1).isNotEqualTo(orderedBookDTO2);
        orderedBookDTO2.setId(orderedBookDTO1.getId());
        assertThat(orderedBookDTO1).isEqualTo(orderedBookDTO2);
        orderedBookDTO2.setId(2L);
        assertThat(orderedBookDTO1).isNotEqualTo(orderedBookDTO2);
        orderedBookDTO1.setId(null);
        assertThat(orderedBookDTO1).isNotEqualTo(orderedBookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderedBookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderedBookMapper.fromId(null)).isNull();
    }
}
