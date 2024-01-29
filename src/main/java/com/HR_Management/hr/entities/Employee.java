package com.HR_Management.hr.entities;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @Column(name = "emp_id")
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String empId;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "department")
    private String department;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "designation")
    private String designation;

    @Column(name = "leaves")
    private Long leaves= 22L;

    @Column(name = "address")
    private String address;

    private String band;

    @Column(name = "joining_date")
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate joiningDate;

    @JsonIncludeProperties({"p_id", "name", "description"})
    @ManyToMany( cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "p_id"))
    private List<Project> projects= new ArrayList<>();

    public Employee(String empId,
                    String name,
                    String email,
                    String department,
                    Float salary,
                    String designation,
                    Long leaves,
                    String address,
                    LocalDate joiningDate,
                    List<Project> projects,
                    List<Leave> employeeLeaves,
                    Long pending_leaves,
                    String band) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
        this.designation = designation;
        this.leaves = leaves;
        this.address = address;
        this.joiningDate = joiningDate;
        this.projects = projects;
        this.band=band;
    }

    public String getBand() {
        return band;
    }

    public void setBand(String band) {
        this.band = band;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Employee(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }
    public LocalDate getJoiningDate() {
        return joiningDate;
    }
    public String getEmpId() {
        return empId;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Employee() {
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

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
