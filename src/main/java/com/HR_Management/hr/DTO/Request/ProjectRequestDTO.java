package com.HR_Management.hr.DTO.Request;

import com.HR_Management.hr.entities.Employee;

import java.util.List;

public class ProjectRequestDTO {
    String name;
    String description;
    public ProjectRequestDTO() {
    }

    public ProjectRequestDTO(String name, String description, List<Employee> employeeList) {
        this.name = name;
        this.description = description;
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
