package com.HR_Management.hr.DTO.Request;
import java.time.LocalDate;
import java.util.Date;

public class EmployeeRequestDTO {
    String name;
    String email;
    String department;
    Float salary;
//    String designation;
    Long leaves=22L;
    String address;
    LocalDate joiningDate;

    public EmployeeRequestDTO() {
    }

    public EmployeeRequestDTO(String name, String email, String department, Float salary, Long leaves, String address, LocalDate joiningDate) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
//        this.designation = designation;
        this.leaves = leaves;
        this.address = address;
        this.joiningDate = joiningDate;
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

    public Long getLeaves() {
        return leaves;
    }

    public void setLeaves(Long leaves) {
        this.leaves = leaves;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
}
