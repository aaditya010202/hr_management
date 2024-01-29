package com.HR_Management.hr.DTO.Response;

public class TimesheetCategorisedResponseDTO {
    Integer count=0;
    Float cost=0f;
    Float hours=0f;

    public TimesheetCategorisedResponseDTO() {
    }

    public TimesheetCategorisedResponseDTO(Integer count, Float cost, Float hours) {
        this.count = count;
        this.cost = cost;
        this.hours = hours;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }
}
