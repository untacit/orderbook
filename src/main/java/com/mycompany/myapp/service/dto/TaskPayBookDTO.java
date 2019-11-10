package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaskPayBook entity.
 */
public class TaskPayBookDTO implements Serializable {

    private Long id;

    private Long orderBookProcessId;

    private String orderBookProcessBusinessKey;

    private String nameOnCard;

    private String creditCard;

    private String expiryDate;

    private String ccv;

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

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCcv() {
        return ccv;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskPayBookDTO taskPayBookDTO = (TaskPayBookDTO) o;
        if(taskPayBookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskPayBookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskPayBookDTO{" +
            "id=" + getId() +
            ", nameOnCard='" + getNameOnCard() + "'" +
            ", creditCard='" + getCreditCard() + "'" +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", ccv='" + getCcv() + "'" +
            "}";
    }
}
