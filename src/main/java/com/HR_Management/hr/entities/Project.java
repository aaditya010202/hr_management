package com.HR_Management.hr.entities;

import jakarta.persistence.*;

//import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "project")

public class Project {

    @Id
    @Column(name = "p_id")
//    @GeneratedValue(strategy = GenerationType.UUID)
    private String p_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "projects", cascade = CascadeType.ALL)
    private List<Employee> employees = new ArrayList<>();

    public Project(String p_id, String p_name, String description, List<Employee> employees) {
        this.p_id = p_id;
        this.name = p_name;
        this.description = description;
        this.employees = employees;
    }

    public Project() {
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
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