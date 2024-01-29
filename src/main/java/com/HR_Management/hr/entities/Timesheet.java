package com.HR_Management.hr.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

@Entity
    @Table(name = "timesheet", uniqueConstraints = {
            @UniqueConstraint(columnNames = {"project_id","date","employee_id"})
    })
public class Timesheet {
    @Id
    String id;

    @Column(name = "employee_id")
    String emp_id;

    @Column(name = "project_id")
    String p_id;

    @Column(name = "date")
    LocalDate date;
    Float hours;


//    LocalTime entryTime;
//    LocalTime exitTime;

    public Timesheet() {
    }

    public Timesheet(String id, String emp_id, String p_id, LocalDate date, Float hours) {
        this.id = id;
        this.emp_id = emp_id;
        this.p_id = p_id;
        this.date = date;
        this.hours = hours;
//        this.entryTime=entryTime;
//        this.exitTime=exitTime;
    }

//    public LocalTime getEntryTime() {
//        return entryTime;
//    }

//    public void setEntryTime(LocalTime entryTime) {
//        this.entryTime = entryTime;
//    }
//
//    public LocalTime getExitTime() {
//        return exitTime;
//    }
//
//    public void setExitTime(LocalTime exitTime) {
//        this.exitTime = exitTime;
//    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }
}
