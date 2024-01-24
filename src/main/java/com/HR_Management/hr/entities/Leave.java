package com.HR_Management.hr.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.data.annotation.ReadOnlyProperty;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Leave")
public class Leave {

    @Id
    @Column(name = "Id")
//    @GeneratedValue(strategy = GenerationType.UUID)
    String Id;

    @Column(name = "employee_id")
    String empId;

    @Column(name = "description")
    String description;

    @Column(name = "status")
    Boolean accepted = false;

    @ManyToOne(targetEntity = Employee.class)
    private Employee updatedBy;

    @Column(name = "type")
    String type;

//    @JsonBackReference
//    @ManyToOne(fetch = FetchType.LAZY)
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL,CascadeType.REMOVE})
//    @JoinColumn(name ="empId")
    private Employee employee;

    @Column(name = "fromDate")
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate fromDate;

    @Column(name = "toDate")
    @Temporal(TemporalType.DATE)
//    @JsonFormat(pattern = "yyyy-mm-dd")
    private LocalDate toDate;

    @Column(name = "holidayDays", columnDefinition =  "bigint GENERATED ALWAYS AS ((to_date - from_date)) STORED" ,insertable = false, updatable = false)
//    @Column(name = "holidayDays")
    private Long holidayDays;


    public Employee getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Employee updatedBy) {
        this.updatedBy = updatedBy;
    }
    public Leave() {
    }

    public Leave(String id, String empId, String description, Boolean accepted, String type, Employee employee, LocalDate fromDate, LocalDate toDate, Long holidayDays,Employee updatedBy) {
        this.Id = id;
        this.empId = empId;
        this.description = description;
        this.accepted = accepted;
        this.type = type;
        this.employee = employee;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.holidayDays = holidayDays;
        this.updatedBy=updatedBy;
    }

    public Long getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(Long holidayDays) {
        this.holidayDays = holidayDays;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        this.Id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
