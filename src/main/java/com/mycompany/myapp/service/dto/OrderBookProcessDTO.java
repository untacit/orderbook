package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.annotation.UntacitDeploymentId;
import com.mycompany.myapp.annotation.UntacitProcessDefinitionId;
import com.mycompany.myapp.annotation.UntacitProcessInstanceBusinessKey;
import com.mycompany.myapp.annotation.UntacitProcessInstanceId;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the OrderBookProcess entity.
 */
public class OrderBookProcessDTO implements Serializable {

    private Long id;

    @UntacitProcessInstanceBusinessKey
    private String businessKey;

    @UntacitDeploymentId
    private String camundaDeploymentId;

    @UntacitProcessDefinitionId
    private String camundaProcessDefinitionId;

    @UntacitProcessInstanceId
    private String camundaProcessInstanceId;

    private OrderBookDomainDTO orderBookDomain;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getCamundaDeploymentId() {
        return camundaDeploymentId;
    }

    public void setCamundaDeploymentId(String camundaDeploymentId) {
        this.camundaDeploymentId = camundaDeploymentId;
    }

    public String getCamundaProcessDefinitionId() {
        return camundaProcessDefinitionId;
    }

    public void setCamundaProcessDefinitionId(String camundaProcessDefinitionId) {
        this.camundaProcessDefinitionId = camundaProcessDefinitionId;
    }

    public String getCamundaProcessInstanceId() {
        return camundaProcessInstanceId;
    }

    public void setCamundaProcessInstanceId(String camundaProcessInstanceId) {
        this.camundaProcessInstanceId = camundaProcessInstanceId;
    }

    public OrderBookDomainDTO getOrderBookDomain() {
        return orderBookDomain;
    }

    public OrderBookProcessDTO orderBookDomain(OrderBookDomainDTO orderBookDomain) {
        this.orderBookDomain = orderBookDomain;
        return this;
    }

    public void setOrderBookDomain(OrderBookDomainDTO orderBookDomain) {
        this.orderBookDomain = orderBookDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderBookProcessDTO orderBookProcessDTO = (OrderBookProcessDTO) o;
        if(orderBookProcessDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), orderBookProcessDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderBookProcessDTO{" +
            "id=" + getId() +
            "businessKey=" + getBusinessKey() +
            "}";
    }
}
