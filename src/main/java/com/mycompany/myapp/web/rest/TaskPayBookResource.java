package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.TaskPayBookService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TaskPayBookDTO;
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
 * REST controller for managing TaskPayBook.
 */
@RestController
@RequestMapping("/api")
public class TaskPayBookResource {

    private final Logger log = LoggerFactory.getLogger(TaskPayBookResource.class);

    private static final String ENTITY_NAME = "taskPayBook";

    private final TaskPayBookService taskPayBookService;

    public TaskPayBookResource(TaskPayBookService taskPayBookService) {
        this.taskPayBookService = taskPayBookService;
    }

    /**
     * POST  /task-pay-books : Create a new taskPayBook.
     *
     * @param taskPayBookDTO the taskPayBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskPayBookDTO, or with status 400 (Bad Request) if the taskPayBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-pay-books")
    @Timed
    public ResponseEntity<TaskPayBookDTO> createTaskPayBook(@RequestBody TaskPayBookDTO taskPayBookDTO) throws URISyntaxException {
        log.debug("REST request to save TaskPayBook : {}", taskPayBookDTO);
        if (taskPayBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskPayBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskPayBookDTO result = taskPayBookService.save(taskPayBookDTO);
        return ResponseEntity.created(new URI("/api/task-pay-books/" + result.getOrderBookProcessId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOrderBookProcessId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-pay-books : Updates an existing taskPayBook.
     *
     * @param taskPayBookDTO the taskPayBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskPayBookDTO,
     * or with status 400 (Bad Request) if the taskPayBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskPayBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-pay-books")
    @Timed
    public ResponseEntity<TaskPayBookDTO> updateTaskPayBook(@RequestBody TaskPayBookDTO taskPayBookDTO) throws URISyntaxException {
        log.debug("REST request to update TaskPayBook : {}", taskPayBookDTO);
        if (taskPayBookDTO.getId() == null) {
            return createTaskPayBook(taskPayBookDTO);
        }
        TaskPayBookDTO result = taskPayBookService.save(taskPayBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskPayBookDTO.getOrderBookProcessId().toString()))
            .body(result);
    }


    /**
     * GET  /task-pay-books/:processInstanceId : get the "processInstanceId" taskPayBook.
     *
     * @param processInstanceId the id of the taskPayBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskPayBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-pay-books/{processInstanceId}")
    @Timed
    public ResponseEntity<TaskPayBookDTO> getTaskPayBook(@PathVariable String processInstanceId) {
        log.debug("REST request to get TaskPayBook : {}", processInstanceId);
        TaskPayBookDTO taskPayBookDTO = taskPayBookService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskPayBookDTO));
    }


}
