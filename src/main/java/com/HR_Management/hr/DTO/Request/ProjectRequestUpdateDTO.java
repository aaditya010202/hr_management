package com.HR_Management.hr.DTO.Request;

import com.HR_Management.hr.entities.Employee;

import java.awt.*;
import java.util.List;

public class ProjectRequestUpdateDTO {
    String name;
    String description;
    Float totalDuration;
    Float totalCost;
    List<String> employees;

    public ProjectRequestUpdateDTO() {
    }

    public ProjectRequestUpdateDTO(String name, String description, List<String> employees, Float totalDuration, Float totalCost) {
        this.name = name;
        this.description = description;
        this.employees = employees;
        this.totalCost=totalCost;
        this.totalDuration=totalDuration;
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

    public List<String> getEmployees() {
        return employees;
    }

    public void setEmployees(List<String> employees) {
        this.employees = employees;
    }
}
