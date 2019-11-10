package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderbookApp;

import com.mycompany.myapp.domain.PaymentDetails;
import com.mycompany.myapp.repository.PaymentDetailsRepository;
import com.mycompany.myapp.service.dto.PaymentDetailsDTO;
import com.mycompany.myapp.service.mapper.PaymentDetailsMapper;
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
 * Test class for the PaymentDetailsResource REST controller.
 *
 * @see PaymentDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderbookApp.class)
public class PaymentDetailsResourceIntTest {

    private static final String DEFAULT_NAME_ON_CARD = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ON_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_CARD = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CCV = "AAAAAAAAAA";
    private static final String UPDATED_CCV = "BBBBBBBBBB";

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @Autowired
    private PaymentDetailsMapper paymentDetailsMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPaymentDetailsMockMvc;

    private PaymentDetails paymentDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PaymentDetailsResource paymentDetailsResource = new PaymentDetailsResource(paymentDetailsRepository, paymentDetailsMapper);
        this.restPaymentDetailsMockMvc = MockMvcBuilders.standaloneSetup(paymentDetailsResource)
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
    public static PaymentDetails createEntity(EntityManager em) {
        PaymentDetails paymentDetails = new PaymentDetails()
            .nameOnCard(DEFAULT_NAME_ON_CARD)
            .creditCard(DEFAULT_CREDIT_CARD)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .ccv(DEFAULT_CCV);
        return paymentDetails;
    }

    @Before
    public void initTest() {
        paymentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentDetails() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getNameOnCard()).isEqualTo(DEFAULT_NAME_ON_CARD);
        assertThat(testPaymentDetails.getCreditCard()).isEqualTo(DEFAULT_CREDIT_CARD);
        assertThat(testPaymentDetails.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testPaymentDetails.getCcv()).isEqualTo(DEFAULT_CCV);
    }

    @Test
    @Transactional
    public void createPaymentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails with an existing ID
        paymentDetails.setId(1L);
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentDetailsMockMvc.perform(post("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get all the paymentDetailsList
        restPaymentDetailsMockMvc.perform(get("/api/payment-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].nameOnCard").value(hasItem(DEFAULT_NAME_ON_CARD.toString())))
            .andExpect(jsonPath("$.[*].creditCard").value(hasItem(DEFAULT_CREDIT_CARD.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].ccv").value(hasItem(DEFAULT_CCV.toString())));
    }

    @Test
    @Transactional
    public void getPaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);

        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", paymentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(paymentDetails.getId().intValue()))
            .andExpect(jsonPath("$.nameOnCard").value(DEFAULT_NAME_ON_CARD.toString()))
            .andExpect(jsonPath("$.creditCard").value(DEFAULT_CREDIT_CARD.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.ccv").value(DEFAULT_CCV.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentDetails() throws Exception {
        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(get("/api/payment-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Update the paymentDetails
        PaymentDetails updatedPaymentDetails = paymentDetailsRepository.findOne(paymentDetails.getId());
        // Disconnect from session so that the updates on updatedPaymentDetails are not directly saved in db
        em.detach(updatedPaymentDetails);
        updatedPaymentDetails
            .nameOnCard(UPDATED_NAME_ON_CARD)
            .creditCard(UPDATED_CREDIT_CARD)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .ccv(UPDATED_CCV);
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(updatedPaymentDetails);

        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isOk());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate);
        PaymentDetails testPaymentDetails = paymentDetailsList.get(paymentDetailsList.size() - 1);
        assertThat(testPaymentDetails.getNameOnCard()).isEqualTo(UPDATED_NAME_ON_CARD);
        assertThat(testPaymentDetails.getCreditCard()).isEqualTo(UPDATED_CREDIT_CARD);
        assertThat(testPaymentDetails.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testPaymentDetails.getCcv()).isEqualTo(UPDATED_CCV);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentDetails() throws Exception {
        int databaseSizeBeforeUpdate = paymentDetailsRepository.findAll().size();

        // Create the PaymentDetails
        PaymentDetailsDTO paymentDetailsDTO = paymentDetailsMapper.toDto(paymentDetails);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPaymentDetailsMockMvc.perform(put("/api/payment-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(paymentDetailsDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentDetails in the database
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePaymentDetails() throws Exception {
        // Initialize the database
        paymentDetailsRepository.saveAndFlush(paymentDetails);
        int databaseSizeBeforeDelete = paymentDetailsRepository.findAll().size();

        // Get the paymentDetails
        restPaymentDetailsMockMvc.perform(delete("/api/payment-details/{id}", paymentDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PaymentDetails> paymentDetailsList = paymentDetailsRepository.findAll();
        assertThat(paymentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDetails.class);
        PaymentDetails paymentDetails1 = new PaymentDetails();
        paymentDetails1.setId(1L);
        PaymentDetails paymentDetails2 = new PaymentDetails();
        paymentDetails2.setId(paymentDetails1.getId());
        assertThat(paymentDetails1).isEqualTo(paymentDetails2);
        paymentDetails2.setId(2L);
        assertThat(paymentDetails1).isNotEqualTo(paymentDetails2);
        paymentDetails1.setId(null);
        assertThat(paymentDetails1).isNotEqualTo(paymentDetails2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentDetailsDTO.class);
        PaymentDetailsDTO paymentDetailsDTO1 = new PaymentDetailsDTO();
        paymentDetailsDTO1.setId(1L);
        PaymentDetailsDTO paymentDetailsDTO2 = new PaymentDetailsDTO();
        assertThat(paymentDetailsDTO1).isNotEqualTo(paymentDetailsDTO2);
        paymentDetailsDTO2.setId(paymentDetailsDTO1.getId());
        assertThat(paymentDetailsDTO1).isEqualTo(paymentDetailsDTO2);
        paymentDetailsDTO2.setId(2L);
        assertThat(paymentDetailsDTO1).isNotEqualTo(paymentDetailsDTO2);
        paymentDetailsDTO1.setId(null);
        assertThat(paymentDetailsDTO1).isNotEqualTo(paymentDetailsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(paymentDetailsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(paymentDetailsMapper.fromId(null)).isNull();
    }
}
