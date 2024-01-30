package com.HR_Management.hr.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

@Entity
@Table(name = "project")

public class Project {

    @Id
    @Column(name = "p_id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "Total duration(hours)")
    private Float totalDuration;
    private Float totalCost;
    @ManyToMany(mappedBy = "projects", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public Project(String id, String p_name, String description, List<Employee> employees, Float totalCost, Float totalDuration) {
        this.id = id;
        this.name = p_name;
        this.description = description;
        this.employees = employees;
        this.totalDuration=totalDuration;
        this.totalCost=totalCost;
    }

    public Project() {
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
