package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaskHandleOrder entity.
 */
public class TaskHandleOrderDTO implements Serializable {

    private Long id;

    private Long orderBookProcessId;

    private String orderBookProcessBusinessKey;

    private String orderStatus;

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

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskHandleOrderDTO taskHandleOrderDTO = (TaskHandleOrderDTO) o;
        if(taskHandleOrderDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskHandleOrderDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskHandleOrderDTO{" +
            "id=" + getId() +
            ", orderStatus='" + getOrderStatus() + "'" +
            "}";
    }
}
