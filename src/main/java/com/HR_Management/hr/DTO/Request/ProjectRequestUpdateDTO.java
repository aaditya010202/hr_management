package com.HR_Management.hr.DTO.Request;

import com.HR_Management.hr.entities.Employee;

import java.util.List;

public class ProjectRequestUpdateDTO {
    String name;
    String description;
    List<String> employees;

    public ProjectRequestUpdateDTO() {
    }

    public ProjectRequestUpdateDTO(String name, String description, List<String> employees) {
        this.name = name;
        this.description = description;
        this.employees = employees;
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
