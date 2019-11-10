package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the TaskSetBuyerInfo entity.
 */
public class TaskSetBuyerInfoDTO implements Serializable {

    private Long id;

    private Long orderBookProcessId;

    private String orderBookProcessBusinessKey;

    private String shipTo;

    private Long buyerId;

    private String buyerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderBookProcessId() {
        return orderBookProcessId;
    }

    public void setOrderBookProcessId(Long orderBookProcessId) {
        this.orderBookProcessId = orderBookProcessId;
    }

    public String getOrderBookProcessBusinessKey() {
        return orderBookProcessBusinessKey;
    }

    public void setOrderBookProcessBusinessKey(String orderBookProcessBusinessKey) {
        this.orderBookProcessBusinessKey = orderBookProcessBusinessKey;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskSetBuyerInfoDTO taskSetBuyerInfoDTO = (TaskSetBuyerInfoDTO) o;
        if(taskSetBuyerInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskSetBuyerInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskSetBuyerInfoDTO{" +
            "id=" + getId() +
            ", shipTo='" + getShipTo() + "'" +
            "}";
    }
}
