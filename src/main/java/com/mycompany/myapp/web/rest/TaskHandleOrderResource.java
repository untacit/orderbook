package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.TaskHandleOrderService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TaskHandleOrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TaskHandleOrder.
 */
@RestController
@RequestMapping("/api")
public class TaskHandleOrderResource {

    private final Logger log = LoggerFactory.getLogger(TaskHandleOrderResource.class);

    private static final String ENTITY_NAME = "taskHandleOrder";

    private final TaskHandleOrderService taskHandleOrderService;

    public TaskHandleOrderResource(TaskHandleOrderService taskHandleOrderService) {
        this.taskHandleOrderService = taskHandleOrderService;
    }

    /**
     * POST  /task-handle-orders : Create a new taskHandleOrder.
     *
     * @param taskHandleOrderDTO the taskHandleOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskHandleOrderDTO, or with status 400 (Bad Request) if the taskHandleOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-handle-orders")
    @Timed
    public ResponseEntity<TaskHandleOrderDTO> createTaskHandleOrder(@RequestBody TaskHandleOrderDTO taskHandleOrderDTO) throws URISyntaxException {
        log.debug("REST request to save TaskHandleOrder : {}", taskHandleOrderDTO);
        if (taskHandleOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskHandleOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskHandleOrderDTO result = taskHandleOrderService.save(taskHandleOrderDTO);
        return ResponseEntity.created(new URI("/api/task-handle-orders/" + result.getOrderBookProcessId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOrderBookProcessId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-handle-orders : Updates an existing taskHandleOrder.
     *
     * @param taskHandleOrderDTO the taskHandleOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskHandleOrderDTO,
     * or with status 400 (Bad Request) if the taskHandleOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskHandleOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-handle-orders")
    @Timed
    public ResponseEntity<TaskHandleOrderDTO> updateTaskHandleOrder(@RequestBody TaskHandleOrderDTO taskHandleOrderDTO) throws URISyntaxException {
        log.debug("REST request to update TaskHandleOrder : {}", taskHandleOrderDTO);
        if (taskHandleOrderDTO.getId() == null) {
            return createTaskHandleOrder(taskHandleOrderDTO);
        }
        TaskHandleOrderDTO result = taskHandleOrderService.save(taskHandleOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskHandleOrderDTO.getOrderBookProcessId().toString()))
            .body(result);
    }


    /**
     * GET  /task-handle-orders/:processInstanceId : get the "processInstanceId" taskHandleOrder.
     *
     * @param processInstanceId the id of the taskHandleOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskHandleOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-handle-orders/{processInstanceId}")
    @Timed
    public ResponseEntity<TaskHandleOrderDTO> getTaskHandleOrder(@PathVariable String processInstanceId) {
        log.debug("REST request to get TaskHandleOrder : {}", processInstanceId);
        TaskHandleOrderDTO taskHandleOrderDTO = taskHandleOrderService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskHandleOrderDTO));
    }


}
