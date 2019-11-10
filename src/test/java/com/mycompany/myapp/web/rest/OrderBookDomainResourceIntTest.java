package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.OrderbookApp;

import com.mycompany.myapp.domain.OrderBookDomain;
import com.mycompany.myapp.repository.OrderBookDomainRepository;
import com.mycompany.myapp.service.OrderBookDomainService;
import com.mycompany.myapp.service.dto.OrderBookDomainDTO;
import com.mycompany.myapp.service.mapper.OrderBookDomainMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrderBookDomainResource REST controller.
 *
 * @see OrderBookDomainResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderbookApp.class)
public class OrderBookDomainResourceIntTest {

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_ORDER_AMMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ORDER_AMMOUNT = new BigDecimal(2);

    private static final String DEFAULT_ORDER_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ON_CARD = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ON_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_CREDIT_CARD = "AAAAAAAAAA";
    private static final String UPDATED_CREDIT_CARD = "BBBBBBBBBB";

    private static final String DEFAULT_EXPIRY_DATE = "AAAAAAAAAA";
    private static final String UPDATED_EXPIRY_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CCV = "AAAAAAAAAA";
    private static final String UPDATED_CCV = "BBBBBBBBBB";

    private static final String DEFAULT_SHIP_TO = "AAAAAAAAAA";
    private static final String UPDATED_SHIP_TO = "BBBBBBBBBB";

    @Autowired
    private OrderBookDomainRepository orderBookDomainRepository;

    @Autowired
    private OrderBookDomainMapper orderBookDomainMapper;

    @Autowired
    private OrderBookDomainService orderBookDomainService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrderBookDomainMockMvc;

    private OrderBookDomain orderBookDomain;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderBookDomainResource orderBookDomainResource = new OrderBookDomainResource(orderBookDomainService);
        this.restOrderBookDomainMockMvc = MockMvcBuilders.standaloneSetup(orderBookDomainResource)
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
    public static OrderBookDomain createEntity(EntityManager em) {
        OrderBookDomain orderBookDomain = new OrderBookDomain()
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .orderAmmount(DEFAULT_ORDER_AMMOUNT)
            .orderStatus(DEFAULT_ORDER_STATUS)
            .nameOnCard(DEFAULT_NAME_ON_CARD)
            .creditCard(DEFAULT_CREDIT_CARD)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .ccv(DEFAULT_CCV)
            .shipTo(DEFAULT_SHIP_TO);
        return orderBookDomain;
    }

    @Before
    public void initTest() {
        orderBookDomain = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderBookDomain() throws Exception {
        int databaseSizeBeforeCreate = orderBookDomainRepository.findAll().size();

        // Create the OrderBookDomain
        OrderBookDomainDTO orderBookDomainDTO = orderBookDomainMapper.toDto(orderBookDomain);
        restOrderBookDomainMockMvc.perform(post("/api/order-book-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderBookDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderBookDomain in the database
        List<OrderBookDomain> orderBookDomainList = orderBookDomainRepository.findAll();
        assertThat(orderBookDomainList).hasSize(databaseSizeBeforeCreate + 1);
        OrderBookDomain testOrderBookDomain = orderBookDomainList.get(orderBookDomainList.size() - 1);
        assertThat(testOrderBookDomain.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOrderBookDomain.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testOrderBookDomain.getOrderAmmount()).isEqualTo(DEFAULT_ORDER_AMMOUNT);
        assertThat(testOrderBookDomain.getOrderStatus()).isEqualTo(DEFAULT_ORDER_STATUS);
        assertThat(testOrderBookDomain.getNameOnCard()).isEqualTo(DEFAULT_NAME_ON_CARD);
        assertThat(testOrderBookDomain.getCreditCard()).isEqualTo(DEFAULT_CREDIT_CARD);
        assertThat(testOrderBookDomain.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testOrderBookDomain.getCcv()).isEqualTo(DEFAULT_CCV);
        assertThat(testOrderBookDomain.getShipTo()).isEqualTo(DEFAULT_SHIP_TO);
    }

    @Test
    @Transactional
    public void createOrderBookDomainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderBookDomainRepository.findAll().size();

        // Create the OrderBookDomain with an existing ID
        orderBookDomain.setId(1L);
        OrderBookDomainDTO orderBookDomainDTO = orderBookDomainMapper.toDto(orderBookDomain);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderBookDomainMockMvc.perform(post("/api/order-book-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderBookDomainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrderBookDomain in the database
        List<OrderBookDomain> orderBookDomainList = orderBookDomainRepository.findAll();
        assertThat(orderBookDomainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrderBookDomains() throws Exception {
        // Initialize the database
        orderBookDomainRepository.saveAndFlush(orderBookDomain);

        // Get all the orderBookDomainList
        restOrderBookDomainMockMvc.perform(get("/api/order-book-domains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderBookDomain.getId().intValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].orderAmmount").value(hasItem(DEFAULT_ORDER_AMMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].orderStatus").value(hasItem(DEFAULT_ORDER_STATUS.toString())))
            .andExpect(jsonPath("$.[*].nameOnCard").value(hasItem(DEFAULT_NAME_ON_CARD.toString())))
            .andExpect(jsonPath("$.[*].creditCard").value(hasItem(DEFAULT_CREDIT_CARD.toString())))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(DEFAULT_EXPIRY_DATE.toString())))
            .andExpect(jsonPath("$.[*].ccv").value(hasItem(DEFAULT_CCV.toString())))
            .andExpect(jsonPath("$.[*].shipTo").value(hasItem(DEFAULT_SHIP_TO.toString())));
    }

    @Test
    @Transactional
    public void getOrderBookDomain() throws Exception {
        // Initialize the database
        orderBookDomainRepository.saveAndFlush(orderBookDomain);

        // Get the orderBookDomain
        restOrderBookDomainMockMvc.perform(get("/api/order-book-domains/{id}", orderBookDomain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderBookDomain.getId().intValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.orderAmmount").value(DEFAULT_ORDER_AMMOUNT.intValue()))
            .andExpect(jsonPath("$.orderStatus").value(DEFAULT_ORDER_STATUS.toString()))
            .andExpect(jsonPath("$.nameOnCard").value(DEFAULT_NAME_ON_CARD.toString()))
            .andExpect(jsonPath("$.creditCard").value(DEFAULT_CREDIT_CARD.toString()))
            .andExpect(jsonPath("$.expiryDate").value(DEFAULT_EXPIRY_DATE.toString()))
            .andExpect(jsonPath("$.ccv").value(DEFAULT_CCV.toString()))
            .andExpect(jsonPath("$.shipTo").value(DEFAULT_SHIP_TO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderBookDomain() throws Exception {
        // Get the orderBookDomain
        restOrderBookDomainMockMvc.perform(get("/api/order-book-domains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderBookDomain() throws Exception {
        // Initialize the database
        orderBookDomainRepository.saveAndFlush(orderBookDomain);
        int databaseSizeBeforeUpdate = orderBookDomainRepository.findAll().size();

        // Update the orderBookDomain
        OrderBookDomain updatedOrderBookDomain = orderBookDomainRepository.findOne(orderBookDomain.getId());
        // Disconnect from session so that the updates on updatedOrderBookDomain are not directly saved in db
        em.detach(updatedOrderBookDomain);
        updatedOrderBookDomain
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .orderAmmount(UPDATED_ORDER_AMMOUNT)
            .orderStatus(UPDATED_ORDER_STATUS)
            .nameOnCard(UPDATED_NAME_ON_CARD)
            .creditCard(UPDATED_CREDIT_CARD)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .ccv(UPDATED_CCV)
            .shipTo(UPDATED_SHIP_TO);
        OrderBookDomainDTO orderBookDomainDTO = orderBookDomainMapper.toDto(updatedOrderBookDomain);

        restOrderBookDomainMockMvc.perform(put("/api/order-book-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderBookDomainDTO)))
            .andExpect(status().isOk());

        // Validate the OrderBookDomain in the database
        List<OrderBookDomain> orderBookDomainList = orderBookDomainRepository.findAll();
        assertThat(orderBookDomainList).hasSize(databaseSizeBeforeUpdate);
        OrderBookDomain testOrderBookDomain = orderBookDomainList.get(orderBookDomainList.size() - 1);
        assertThat(testOrderBookDomain.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOrderBookDomain.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOrderBookDomain.getOrderAmmount()).isEqualTo(UPDATED_ORDER_AMMOUNT);
        assertThat(testOrderBookDomain.getOrderStatus()).isEqualTo(UPDATED_ORDER_STATUS);
        assertThat(testOrderBookDomain.getNameOnCard()).isEqualTo(UPDATED_NAME_ON_CARD);
        assertThat(testOrderBookDomain.getCreditCard()).isEqualTo(UPDATED_CREDIT_CARD);
        assertThat(testOrderBookDomain.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testOrderBookDomain.getCcv()).isEqualTo(UPDATED_CCV);
        assertThat(testOrderBookDomain.getShipTo()).isEqualTo(UPDATED_SHIP_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderBookDomain() throws Exception {
        int databaseSizeBeforeUpdate = orderBookDomainRepository.findAll().size();

        // Create the OrderBookDomain
        OrderBookDomainDTO orderBookDomainDTO = orderBookDomainMapper.toDto(orderBookDomain);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrderBookDomainMockMvc.perform(put("/api/order-book-domains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderBookDomainDTO)))
            .andExpect(status().isCreated());

        // Validate the OrderBookDomain in the database
        List<OrderBookDomain> orderBookDomainList = orderBookDomainRepository.findAll();
        assertThat(orderBookDomainList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrderBookDomain() throws Exception {
        // Initialize the database
        orderBookDomainRepository.saveAndFlush(orderBookDomain);
        int databaseSizeBeforeDelete = orderBookDomainRepository.findAll().size();

        // Get the orderBookDomain
        restOrderBookDomainMockMvc.perform(delete("/api/order-book-domains/{id}", orderBookDomain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrderBookDomain> orderBookDomainList = orderBookDomainRepository.findAll();
        assertThat(orderBookDomainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderBookDomain.class);
        OrderBookDomain orderBookDomain1 = new OrderBookDomain();
        orderBookDomain1.setId(1L);
        OrderBookDomain orderBookDomain2 = new OrderBookDomain();
        orderBookDomain2.setId(orderBookDomain1.getId());
        assertThat(orderBookDomain1).isEqualTo(orderBookDomain2);
        orderBookDomain2.setId(2L);
        assertThat(orderBookDomain1).isNotEqualTo(orderBookDomain2);
        orderBookDomain1.setId(null);
        assertThat(orderBookDomain1).isNotEqualTo(orderBookDomain2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderBookDomainDTO.class);
        OrderBookDomainDTO orderBookDomainDTO1 = new OrderBookDomainDTO();
        orderBookDomainDTO1.setId(1L);
        OrderBookDomainDTO orderBookDomainDTO2 = new OrderBookDomainDTO();
        assertThat(orderBookDomainDTO1).isNotEqualTo(orderBookDomainDTO2);
        orderBookDomainDTO2.setId(orderBookDomainDTO1.getId());
        assertThat(orderBookDomainDTO1).isEqualTo(orderBookDomainDTO2);
        orderBookDomainDTO2.setId(2L);
        assertThat(orderBookDomainDTO1).isNotEqualTo(orderBookDomainDTO2);
        orderBookDomainDTO1.setId(null);
        assertThat(orderBookDomainDTO1).isNotEqualTo(orderBookDomainDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(orderBookDomainMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(orderBookDomainMapper.fromId(null)).isNull();
    }
}
