package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderbookApp;

import com.mycompany.myapp.domain.Buyer;
import com.mycompany.myapp.repository.BuyerRepository;
import com.mycompany.myapp.service.dto.BuyerDTO;
import com.mycompany.myapp.service.mapper.BuyerMapper;
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
 * Test class for the BuyerResource REST controller.
 *
 * @see BuyerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderbookApp.class)
public class BuyerResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_STREET_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_STREET_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_STATE_PROVINCE = "AAAAAAAAAA";
    private static final String UPDATED_STATE_PROVINCE = "BBBBBBBBBB";

    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private BuyerMapper buyerMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBuyerMockMvc;

    private Buyer buyer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BuyerResource buyerResource = new BuyerResource(buyerRepository, buyerMapper);
        this.restBuyerMockMvc = MockMvcBuilders.standaloneSetup(buyerResource)
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
    public static Buyer createEntity(EntityManager em) {
        Buyer buyer = new Buyer()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .streetAddress(DEFAULT_STREET_ADDRESS)
            .postalCode(DEFAULT_POSTAL_CODE)
            .city(DEFAULT_CITY)
            .stateProvince(DEFAULT_STATE_PROVINCE);
        return buyer;
    }

    @Before
    public void initTest() {
        buyer = createEntity(em);
    }

    @Test
    @Transactional
    public void createBuyer() throws Exception {
        int databaseSizeBeforeCreate = buyerRepository.findAll().size();

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);
        restBuyerMockMvc.perform(post("/api/buyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isCreated());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeCreate + 1);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBuyer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testBuyer.getStreetAddress()).isEqualTo(DEFAULT_STREET_ADDRESS);
        assertThat(testBuyer.getPostalCode()).isEqualTo(DEFAULT_POSTAL_CODE);
        assertThat(testBuyer.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testBuyer.getStateProvince()).isEqualTo(DEFAULT_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void createBuyerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = buyerRepository.findAll().size();

        // Create the Buyer with an existing ID
        buyer.setId(1L);
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBuyerMockMvc.perform(post("/api/buyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBuyers() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        // Get all the buyerList
        restBuyerMockMvc.perform(get("/api/buyers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(buyer.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].streetAddress").value(hasItem(DEFAULT_STREET_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].postalCode").value(hasItem(DEFAULT_POSTAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].stateProvince").value(hasItem(DEFAULT_STATE_PROVINCE.toString())));
    }

    @Test
    @Transactional
    public void getBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);

        // Get the buyer
        restBuyerMockMvc.perform(get("/api/buyers/{id}", buyer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(buyer.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.streetAddress").value(DEFAULT_STREET_ADDRESS.toString()))
            .andExpect(jsonPath("$.postalCode").value(DEFAULT_POSTAL_CODE.toString()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.stateProvince").value(DEFAULT_STATE_PROVINCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBuyer() throws Exception {
        // Get the buyer
        restBuyerMockMvc.perform(get("/api/buyers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Update the buyer
        Buyer updatedBuyer = buyerRepository.findOne(buyer.getId());
        // Disconnect from session so that the updates on updatedBuyer are not directly saved in db
        em.detach(updatedBuyer);
        updatedBuyer
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .streetAddress(UPDATED_STREET_ADDRESS)
            .postalCode(UPDATED_POSTAL_CODE)
            .city(UPDATED_CITY)
            .stateProvince(UPDATED_STATE_PROVINCE);
        BuyerDTO buyerDTO = buyerMapper.toDto(updatedBuyer);

        restBuyerMockMvc.perform(put("/api/buyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isOk());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate);
        Buyer testBuyer = buyerList.get(buyerList.size() - 1);
        assertThat(testBuyer.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBuyer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testBuyer.getStreetAddress()).isEqualTo(UPDATED_STREET_ADDRESS);
        assertThat(testBuyer.getPostalCode()).isEqualTo(UPDATED_POSTAL_CODE);
        assertThat(testBuyer.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testBuyer.getStateProvince()).isEqualTo(UPDATED_STATE_PROVINCE);
    }

    @Test
    @Transactional
    public void updateNonExistingBuyer() throws Exception {
        int databaseSizeBeforeUpdate = buyerRepository.findAll().size();

        // Create the Buyer
        BuyerDTO buyerDTO = buyerMapper.toDto(buyer);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBuyerMockMvc.perform(put("/api/buyers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(buyerDTO)))
            .andExpect(status().isCreated());

        // Validate the Buyer in the database
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBuyer() throws Exception {
        // Initialize the database
        buyerRepository.saveAndFlush(buyer);
        int databaseSizeBeforeDelete = buyerRepository.findAll().size();

        // Get the buyer
        restBuyerMockMvc.perform(delete("/api/buyers/{id}", buyer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Buyer> buyerList = buyerRepository.findAll();
        assertThat(buyerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Buyer.class);
        Buyer buyer1 = new Buyer();
        buyer1.setId(1L);
        Buyer buyer2 = new Buyer();
        buyer2.setId(buyer1.getId());
        assertThat(buyer1).isEqualTo(buyer2);
        buyer2.setId(2L);
        assertThat(buyer1).isNotEqualTo(buyer2);
        buyer1.setId(null);
        assertThat(buyer1).isNotEqualTo(buyer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(BuyerDTO.class);
        BuyerDTO buyerDTO1 = new BuyerDTO();
        buyerDTO1.setId(1L);
        BuyerDTO buyerDTO2 = new BuyerDTO();
        assertThat(buyerDTO1).isNotEqualTo(buyerDTO2);
        buyerDTO2.setId(buyerDTO1.getId());
        assertThat(buyerDTO1).isEqualTo(buyerDTO2);
        buyerDTO2.setId(2L);
        assertThat(buyerDTO1).isNotEqualTo(buyerDTO2);
        buyerDTO1.setId(null);
        assertThat(buyerDTO1).isNotEqualTo(buyerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(buyerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(buyerMapper.fromId(null)).isNull();
    }
}
