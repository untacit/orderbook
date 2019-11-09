package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.ProcessDefinition;
import com.mycompany.myapp.service.ProcessDefinitionService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing ProcessDefinition.
 */
@RestController
@RequestMapping("/api")
public class ProcessDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionResource.class);

    private static final String ENTITY_NAME = "processDefinition";

    private final ProcessDefinitionService processDefinitionService;

    public ProcessDefinitionResource(ProcessDefinitionService processDefinitionService) {
        this.processDefinitionService = processDefinitionService;
    }

    /**
     * POST  /process-definitions : Create a new processDefinition.
     *
     * @param processDefinition the processDefinition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new processDefinition, or with status 400 (Bad Request) if the processDefinition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/process-definitions")
    @Timed
    public ResponseEntity<ProcessDefinition> createProcessDefinition(@RequestBody ProcessDefinition processDefinition) throws URISyntaxException {
        log.debug("REST request to save ProcessDefinition : {}", processDefinition);
        if (processDefinition.getId() != null) {
            throw new BadRequestAlertException("A new processDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessDefinition result = processDefinitionService.save(processDefinition);
        return ResponseEntity.created(new URI("/api/process-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /process-definitions : Updates an existing processDefinition.
     *
     * @param processDefinition the processDefinition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated processDefinition,
     * or with status 400 (Bad Request) if the processDefinition is not valid,
     * or with status 500 (Internal Server Error) if the processDefinition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/process-definitions")
    @Timed
    public ResponseEntity<ProcessDefinition> updateProcessDefinition(@RequestBody ProcessDefinition processDefinition) throws URISyntaxException {
        log.debug("REST request to update ProcessDefinition : {}", processDefinition);
        if (processDefinition.getId() == null) {
            return createProcessDefinition(processDefinition);
        }
        ProcessDefinition result = processDefinitionService.save(processDefinition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, processDefinition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /process-definitions : get all the processDefinitions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of processDefinitions in body
     */
    @GetMapping("/process-definitions")
    @Timed
    public List<ProcessDefinition> getAllProcessDefinitions() {
        log.debug("REST request to get all ProcessDefinitions");
        return processDefinitionService.findAll();
        }

    /**
     * GET  /process-definitions/:id : get the "id" processDefinition.
     *
     * @param id the id of the processDefinition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the processDefinition, or with status 404 (Not Found)
     */
    @GetMapping("/process-definitions/{id}")
    @Timed
    public ResponseEntity<ProcessDefinition> getProcessDefinition(@PathVariable Long id) {
        log.debug("REST request to get ProcessDefinition : {}", id);
        ProcessDefinition processDefinition = processDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(processDefinition));
    }

    /**
     * DELETE  /process-definitions/:id : delete the "id" processDefinition.
     *
     * @param id the id of the processDefinition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/process-definitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcessDefinition(@PathVariable Long id) {
        log.debug("REST request to delete ProcessDefinition : {}", id);
        processDefinitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
