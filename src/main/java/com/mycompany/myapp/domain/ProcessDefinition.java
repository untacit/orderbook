package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ProcessDefinition.
 */
@Entity
@Table(name = "process_definition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProcessDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "specification_file")
    private byte[] specificationFile;

    @Column(name = "specification_file_content_type")
    private String specificationFileContentType;

    @Column(name = "camunda_deployment_id")
    private String camundaDeploymentId;

    @Column(name = "camunda_process_definition_id")
    private String camundaProcessDefinitionId;

    @Column(name = "bpmn_process_definition_id")
    private String bpmnProcessDefinitionId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProcessDefinition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getSpecificationFile() {
        return specificationFile;
    }

    public ProcessDefinition specificationFile(byte[] specificationFile) {
        this.specificationFile = specificationFile;
        return this;
    }

    public void setSpecificationFile(byte[] specificationFile) {
        this.specificationFile = specificationFile;
    }

    public String getSpecificationFileContentType() {
        return specificationFileContentType;
    }

    public ProcessDefinition specificationFileContentType(String specificationFileContentType) {
        this.specificationFileContentType = specificationFileContentType;
        return this;
    }

    public void setSpecificationFileContentType(String specificationFileContentType) {
        this.specificationFileContentType = specificationFileContentType;
    }

    public String getCamundaDeploymentId() {
        return camundaDeploymentId;
    }

    public ProcessDefinition camundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
        return this;
    }

    public void setCamundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
    }

    public String getCamundaProcessDefinitionId() {
        return camundaProcessDefinitionId;
    }

    public ProcessDefinition camundaProcessDefinitionId(String camundaProcessDefinitionId) {
        this.camundaProcessDefinitionId = camundaProcessDefinitionId;
        return this;
    }

    public void setCamundaProcessDefinitionId(String camundaProcessDefinitionId) {
        this.camundaProcessDefinitionId = camundaProcessDefinitionId;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public ProcessDefinition bpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
        return this;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProcessDefinition processDefinition = (ProcessDefinition) o;
        if (processDefinition.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), processDefinition.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProcessDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", specificationFile='" + getSpecificationFile() + "'" +
            ", specificationFileContentType='" + getSpecificationFileContentType() + "'" +
            ", camundaDeploymentId='" + getCamundaDeploymentId() + "'" +
            ", camundaProcessDefinitionId='" + getCamundaProcessDefinitionId() + "'" +
            ", bpmnProcessDefinitionId='" + getBpmnProcessDefinitionId() + "'" +
            "}";
    }
}
