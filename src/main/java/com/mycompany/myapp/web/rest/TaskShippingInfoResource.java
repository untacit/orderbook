package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.TaskShippingInfoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TaskShippingInfoDTO;
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
 * REST controller for managing TaskShippingInfo.
 */
@RestController
@RequestMapping("/api")
public class TaskShippingInfoResource {

    private final Logger log = LoggerFactory.getLogger(TaskShippingInfoResource.class);

    private static final String ENTITY_NAME = "taskShippingInfo";

    private final TaskShippingInfoService taskShippingInfoService;

    public TaskShippingInfoResource(TaskShippingInfoService taskShippingInfoService) {
        this.taskShippingInfoService = taskShippingInfoService;
    }

    /**
     * POST  /task-shipping-infos : Create a new taskShippingInfo.
     *
     * @param taskShippingInfoDTO the taskShippingInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskShippingInfoDTO, or with status 400 (Bad Request) if the taskShippingInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-shipping-infos")
    @Timed
    public ResponseEntity<TaskShippingInfoDTO> createTaskShippingInfo(@RequestBody TaskShippingInfoDTO taskShippingInfoDTO) throws URISyntaxException {
        log.debug("REST request to save TaskShippingInfo : {}", taskShippingInfoDTO);
        if (taskShippingInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskShippingInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskShippingInfoDTO result = taskShippingInfoService.save(taskShippingInfoDTO);
        return ResponseEntity.created(new URI("/api/task-shipping-infos/" + result.getOrderBookProcessId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOrderBookProcessId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-shipping-infos : Updates an existing taskShippingInfo.
     *
     * @param taskShippingInfoDTO the taskShippingInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskShippingInfoDTO,
     * or with status 400 (Bad Request) if the taskShippingInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskShippingInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-shipping-infos")
    @Timed
    public ResponseEntity<TaskShippingInfoDTO> updateTaskShippingInfo(@RequestBody TaskShippingInfoDTO taskShippingInfoDTO) throws URISyntaxException {
        log.debug("REST request to update TaskShippingInfo : {}", taskShippingInfoDTO);
        if (taskShippingInfoDTO.getId() == null) {
            return createTaskShippingInfo(taskShippingInfoDTO);
        }
        TaskShippingInfoDTO result = taskShippingInfoService.save(taskShippingInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskShippingInfoDTO.getOrderBookProcessId().toString()))
            .body(result);
    }


    /**
     * GET  /task-shipping-infos/:processInstanceId : get the "processInstanceId" taskShippingInfo.
     *
     * @param processInstanceId the id of the taskShippingInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskShippingInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-shipping-infos/{processInstanceId}")
    @Timed
    public ResponseEntity<TaskShippingInfoDTO> getTaskShippingInfo(@PathVariable String processInstanceId) {
        log.debug("REST request to get TaskShippingInfo : {}", processInstanceId);
        TaskShippingInfoDTO taskShippingInfoDTO = taskShippingInfoService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskShippingInfoDTO));
    }


}
