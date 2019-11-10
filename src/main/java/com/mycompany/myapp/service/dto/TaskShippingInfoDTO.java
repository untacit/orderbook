package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the TaskShippingInfo entity.
 */
public class TaskShippingInfoDTO implements Serializable {

    private Long id;

    private Long orderBookProcessId;

    private String orderBookProcessBusinessKey;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TaskShippingInfoDTO taskShippingInfoDTO = (TaskShippingInfoDTO) o;
        if(taskShippingInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), taskShippingInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TaskShippingInfoDTO{" +
            "id=" + getId() +
            "}";
    }
}
