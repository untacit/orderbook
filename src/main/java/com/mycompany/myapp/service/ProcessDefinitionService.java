package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.ProcessDefinition;
import com.mycompany.myapp.repository.ProcessDefinitionRepository;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * Service Implementation for managing ProcessDefinition.
 */
@Service
@Transactional
public class ProcessDefinitionService {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionService.class);

    private final ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private RepositoryService repositoryService;

    public ProcessDefinitionService(ProcessDefinitionRepository processDefinitionRepository) {
        this.processDefinitionRepository = processDefinitionRepository;
    }

    /**
     * Save a processDefinition.
     *
     * @param processDefinition the entity to save
     * @return the persisted entity
     */
    public ProcessDefinition save(ProcessDefinition processDefinition) {
        log.debug("Request to save ProcessDefinition : {}", processDefinition);
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(new ByteArrayInputStream(processDefinition.getSpecificationFile()));

        org.camunda.bpm.engine.repository.Deployment camundaDeployment = repositoryService.createDeployment()
            .addModelInstance(processDefinition.getBpmnProcessDefinitionId() + ".bpmn", bpmnModelInstance)
            .name(processDefinition.getBpmnProcessDefinitionId())
            .deploy();

        org.camunda.bpm.engine.repository.ProcessDefinition camundaProcessDefinition = repositoryService.createProcessDefinitionQuery()
            .deploymentId(camundaDeployment.getId())
            .singleResult();

        processDefinition.setCamundaDeploymentId(camundaDeployment.getId());
        processDefinition.setCamundaProcessDefinitionId(camundaProcessDefinition.getId());

        return processDefinitionRepository.save(processDefinition);
    }

    /**
     * Get all the processDefinitions.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProcessDefinition> findAll() {
        log.debug("Request to get all ProcessDefinitions");
        return processDefinitionRepository.findAll();
    }

    /**
     * Get one processDefinition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public ProcessDefinition findOne(Long id) {
        log.debug("Request to get ProcessDefinition : {}", id);
        return processDefinitionRepository.findOne(id);
    }

    /**
     * Delete the processDefinition by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessDefinition : {}", id);
        processDefinitionRepository.delete(id);
    }
}
