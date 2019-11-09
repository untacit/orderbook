package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * A Task.
 */
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String description;

    private Date createDate;

    private Date dueDate;

    private Date startTime;

    private Date endTime;

    private String processDefinitionId;

    private String processInstanceId;

    private String owner;

    private String assignee;

    private String executionId;

    private Date createTime;

    private String taskDefinitionKey;

    private Boolean suspended;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Task description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public Task createDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public Task dueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Task startTime(Date startTime) {
        this.startTime = startTime;
        return this;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Task endTime(Date endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public Task processDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
        return this;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public Task processInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
        return this;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getOwner() {
        return owner;
    }

    public Task owner(String owner) {
        this.owner = owner;
        return this;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAssignee() {
        return assignee;
    }

    public Task assignee(String assignee) {
        this.assignee = assignee;
        return this;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getExecutionId() {
        return executionId;
    }

    public Task executionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Task createTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public Task taskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
        return this;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public Boolean isSuspended() {
        return suspended;
    }

    public Task suspended(Boolean suspended) {
        this.suspended = suspended;
        return this;
    }

    public void setSuspended(Boolean suspended) {
        this.suspended = suspended;
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", dueDate='" + getDueDate() + "'" +
            ", startTime='" + getStartTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            ", processDefinitionId='" + getProcessDefinitionId() + "'" +
            ", processInstanceId='" + getProcessInstanceId() + "'" +
            ", owner='" + getOwner() + "'" +
            ", assignee='" + getAssignee() + "'" +
            ", executionId='" + getExecutionId() + "'" +
            ", createTime='" + getCreateTime() + "'" +
            ", taskDefinitionKey='" + getTaskDefinitionKey() + "'" +
            ", suspended='" + isSuspended() + "'" +
            "}";
    }
}
