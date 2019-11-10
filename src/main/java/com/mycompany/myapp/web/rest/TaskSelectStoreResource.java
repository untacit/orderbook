package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.TaskSelectStoreService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TaskSelectStoreDTO;
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
 * REST controller for managing TaskSelectStore.
 */
@RestController
@RequestMapping("/api")
public class TaskSelectStoreResource {

    private final Logger log = LoggerFactory.getLogger(TaskSelectStoreResource.class);

    private static final String ENTITY_NAME = "taskSelectStore";

    private final TaskSelectStoreService taskSelectStoreService;

    public TaskSelectStoreResource(TaskSelectStoreService taskSelectStoreService) {
        this.taskSelectStoreService = taskSelectStoreService;
    }

    /**
     * POST  /task-select-stores : Create a new taskSelectStore.
     *
     * @param taskSelectStoreDTO the taskSelectStoreDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskSelectStoreDTO, or with status 400 (Bad Request) if the taskSelectStore has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-select-stores")
    @Timed
    public ResponseEntity<TaskSelectStoreDTO> createTaskSelectStore(@RequestBody TaskSelectStoreDTO taskSelectStoreDTO) throws URISyntaxException {
        log.debug("REST request to save TaskSelectStore : {}", taskSelectStoreDTO);
        if (taskSelectStoreDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskSelectStore cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskSelectStoreDTO result = taskSelectStoreService.save(taskSelectStoreDTO);
        return ResponseEntity.created(new URI("/api/task-select-stores/" + result.getOrderBookProcessId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOrderBookProcessId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-select-stores : Updates an existing taskSelectStore.
     *
     * @param taskSelectStoreDTO the taskSelectStoreDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskSelectStoreDTO,
     * or with status 400 (Bad Request) if the taskSelectStoreDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskSelectStoreDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-select-stores")
    @Timed
    public ResponseEntity<TaskSelectStoreDTO> updateTaskSelectStore(@RequestBody TaskSelectStoreDTO taskSelectStoreDTO) throws URISyntaxException {
        log.debug("REST request to update TaskSelectStore : {}", taskSelectStoreDTO);
        if (taskSelectStoreDTO.getId() == null) {
            return createTaskSelectStore(taskSelectStoreDTO);
        }
        TaskSelectStoreDTO result = taskSelectStoreService.save(taskSelectStoreDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskSelectStoreDTO.getOrderBookProcessId().toString()))
            .body(result);
    }


    /**
     * GET  /task-select-stores/:processInstanceId : get the "processInstanceId" taskSelectStore.
     *
     * @param processInstanceId the id of the taskSelectStoreDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskSelectStoreDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-select-stores/{processInstanceId}")
    @Timed
    public ResponseEntity<TaskSelectStoreDTO> getTaskSelectStore(@PathVariable String processInstanceId) {
        log.debug("REST request to get TaskSelectStore : {}", processInstanceId);
        TaskSelectStoreDTO taskSelectStoreDTO = taskSelectStoreService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskSelectStoreDTO));
    }


}
