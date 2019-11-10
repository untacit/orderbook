package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.TaskSetBuyerInfoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TaskSetBuyerInfoDTO;
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
 * REST controller for managing TaskSetBuyerInfo.
 */
@RestController
@RequestMapping("/api")
public class TaskSetBuyerInfoResource {

    private final Logger log = LoggerFactory.getLogger(TaskSetBuyerInfoResource.class);

    private static final String ENTITY_NAME = "taskSetBuyerInfo";

    private final TaskSetBuyerInfoService taskSetBuyerInfoService;

    public TaskSetBuyerInfoResource(TaskSetBuyerInfoService taskSetBuyerInfoService) {
        this.taskSetBuyerInfoService = taskSetBuyerInfoService;
    }

    /**
     * POST  /task-set-buyer-infos : Create a new taskSetBuyerInfo.
     *
     * @param taskSetBuyerInfoDTO the taskSetBuyerInfoDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskSetBuyerInfoDTO, or with status 400 (Bad Request) if the taskSetBuyerInfo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-set-buyer-infos")
    @Timed
    public ResponseEntity<TaskSetBuyerInfoDTO> createTaskSetBuyerInfo(@RequestBody TaskSetBuyerInfoDTO taskSetBuyerInfoDTO) throws URISyntaxException {
        log.debug("REST request to save TaskSetBuyerInfo : {}", taskSetBuyerInfoDTO);
        if (taskSetBuyerInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskSetBuyerInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskSetBuyerInfoDTO result = taskSetBuyerInfoService.save(taskSetBuyerInfoDTO);
        return ResponseEntity.created(new URI("/api/task-set-buyer-infos/" + result.getOrderBookProcessId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOrderBookProcessId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-set-buyer-infos : Updates an existing taskSetBuyerInfo.
     *
     * @param taskSetBuyerInfoDTO the taskSetBuyerInfoDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskSetBuyerInfoDTO,
     * or with status 400 (Bad Request) if the taskSetBuyerInfoDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskSetBuyerInfoDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-set-buyer-infos")
    @Timed
    public ResponseEntity<TaskSetBuyerInfoDTO> updateTaskSetBuyerInfo(@RequestBody TaskSetBuyerInfoDTO taskSetBuyerInfoDTO) throws URISyntaxException {
        log.debug("REST request to update TaskSetBuyerInfo : {}", taskSetBuyerInfoDTO);
        if (taskSetBuyerInfoDTO.getId() == null) {
            return createTaskSetBuyerInfo(taskSetBuyerInfoDTO);
        }
        TaskSetBuyerInfoDTO result = taskSetBuyerInfoService.save(taskSetBuyerInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskSetBuyerInfoDTO.getOrderBookProcessId().toString()))
            .body(result);
    }


    /**
     * GET  /task-set-buyer-infos/:processInstanceId : get the "processInstanceId" taskSetBuyerInfo.
     *
     * @param processInstanceId the id of the taskSetBuyerInfoDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskSetBuyerInfoDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-set-buyer-infos/{processInstanceId}")
    @Timed
    public ResponseEntity<TaskSetBuyerInfoDTO> getTaskSetBuyerInfo(@PathVariable String processInstanceId) {
        log.debug("REST request to get TaskSetBuyerInfo : {}", processInstanceId);
        TaskSetBuyerInfoDTO taskSetBuyerInfoDTO = taskSetBuyerInfoService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskSetBuyerInfoDTO));
    }


}
