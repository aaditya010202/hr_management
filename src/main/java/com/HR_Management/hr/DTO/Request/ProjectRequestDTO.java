package com.HR_Management.hr.DTO.Request;

import com.HR_Management.hr.entities.Employee;

import java.util.List;

public class ProjectRequestDTO {
    String name;
    String description;

    Float totalDuration;
    Float totalCost;
    public ProjectRequestDTO() {
    }

    public ProjectRequestDTO(String name, String description, List<Employee> employeeList, Float totalDuration, Float totalCost) {
        this.name = name;
        this.description = description;
        this.totalDuration=totalDuration;
        this.totalCost=totalCost;
    }

    public Float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Float totalDuration) {
        this.totalDuration = totalDuration;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
