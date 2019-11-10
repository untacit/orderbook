package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.service.TaskSelectBookService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.service.dto.TaskSelectBookDTO;
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
 * REST controller for managing TaskSelectBook.
 */
@RestController
@RequestMapping("/api")
public class TaskSelectBookResource {

    private final Logger log = LoggerFactory.getLogger(TaskSelectBookResource.class);

    private static final String ENTITY_NAME = "taskSelectBook";

    private final TaskSelectBookService taskSelectBookService;

    public TaskSelectBookResource(TaskSelectBookService taskSelectBookService) {
        this.taskSelectBookService = taskSelectBookService;
    }

    /**
     * POST  /task-select-books : Create a new taskSelectBook.
     *
     * @param taskSelectBookDTO the taskSelectBookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new taskSelectBookDTO, or with status 400 (Bad Request) if the taskSelectBook has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/task-select-books")
    @Timed
    public ResponseEntity<TaskSelectBookDTO> createTaskSelectBook(@RequestBody TaskSelectBookDTO taskSelectBookDTO) throws URISyntaxException {
        log.debug("REST request to save TaskSelectBook : {}", taskSelectBookDTO);
        if (taskSelectBookDTO.getId() != null) {
            throw new BadRequestAlertException("A new taskSelectBook cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskSelectBookDTO result = taskSelectBookService.save(taskSelectBookDTO);
        return ResponseEntity.created(new URI("/api/task-select-books/" + result.getOrderBookProcessId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getOrderBookProcessId().toString()))
            .body(result);
    }

    /**
     * PUT  /task-select-books : Updates an existing taskSelectBook.
     *
     * @param taskSelectBookDTO the taskSelectBookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated taskSelectBookDTO,
     * or with status 400 (Bad Request) if the taskSelectBookDTO is not valid,
     * or with status 500 (Internal Server Error) if the taskSelectBookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/task-select-books")
    @Timed
    public ResponseEntity<TaskSelectBookDTO> updateTaskSelectBook(@RequestBody TaskSelectBookDTO taskSelectBookDTO) throws URISyntaxException {
        log.debug("REST request to update TaskSelectBook : {}", taskSelectBookDTO);
        if (taskSelectBookDTO.getId() == null) {
            return createTaskSelectBook(taskSelectBookDTO);
        }
        TaskSelectBookDTO result = taskSelectBookService.save(taskSelectBookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, taskSelectBookDTO.getOrderBookProcessId().toString()))
            .body(result);
    }


    /**
     * GET  /task-select-books/:processInstanceId : get the "processInstanceId" taskSelectBook.
     *
     * @param processInstanceId the id of the taskSelectBookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the taskSelectBookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/task-select-books/{processInstanceId}")
    @Timed
    public ResponseEntity<TaskSelectBookDTO> getTaskSelectBook(@PathVariable String processInstanceId) {
        log.debug("REST request to get TaskSelectBook : {}", processInstanceId);
        TaskSelectBookDTO taskSelectBookDTO = taskSelectBookService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(taskSelectBookDTO));
    }


}
