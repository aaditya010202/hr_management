package com.HR_Management.hr.DTO.Response;


import com.HR_Management.hr.entities.Leave;
import com.HR_Management.hr.entities.Project;

import java.util.List;

public class EmployeeResponseDTO {
    String empId;
    String name;
    String email;
    String department;
    Float salary;
    String designation;
    Long leaves;
    List<Project> projectList;



    public EmployeeResponseDTO() {
    }

    public EmployeeResponseDTO(List<Project> projectList,String empId, String name, String email, String department, Float salary, String designation, Long leaves) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.designation = designation;
        this.leaves = leaves;
        this.projectList = projectList;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Long getLeaves() {
        return leaves;
    }

    public void setLeaves(Long leaves) {
        this.leaves = leaves;
    }
}
