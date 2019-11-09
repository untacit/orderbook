package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Task;
import com.mycompany.myapp.service.TasklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing Task.
 */
@RestController
@RequestMapping("/api")
public class TaskResource {

    private final Logger log = LoggerFactory.getLogger(TaskResource.class);

    @Autowired
    private TasklistService tasklistService;


    /**
     * GET  /tasks : get all the tasks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tasks in body
     */
    @GetMapping("/tasks")
    @Timed
    public List<Task> getAllTasks() {
        log.debug("REST request to get all Tasks");
        return tasklistService.findAll();
    }

    /**
     * GET  /tasks : get my tasks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tasks in body
     */
    @GetMapping("/mytasks")
    @Timed
    public List<Task> geMyTasks() {
        log.debug("REST request to get all Tasks");
        return tasklistService.myTasks();
    }


}
