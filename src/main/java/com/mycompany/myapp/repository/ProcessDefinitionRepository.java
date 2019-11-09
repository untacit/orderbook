package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.ProcessDefinition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ProcessDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessDefinitionRepository extends JpaRepository<ProcessDefinition, Long> {

    public ProcessDefinition findByBpmnProcessDefinitionId(String bpmnProcessDefinitionId);

}
