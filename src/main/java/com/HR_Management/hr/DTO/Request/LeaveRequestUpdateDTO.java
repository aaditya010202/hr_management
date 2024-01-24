package com.HR_Management.hr.DTO.Request;

import java.time.LocalDate;
import java.util.Date;

public class LeaveRequestUpdateDTO {
    String empId;
    String description;
    String type;
    LocalDate from_date;
    LocalDate to_date;
    Boolean accepted;

    public LeaveRequestUpdateDTO() {
    }

    public LeaveRequestUpdateDTO(String empId, String description, String type, LocalDate from_date, LocalDate to_date, Boolean accepted) {
        this.empId = empId;
        this.description = description;
        this.type = type;
        this.from_date = from_date;
        this.to_date = to_date;
        this.accepted = accepted;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getFrom_date() {
        return from_date;
    }

    public void setFrom_date(LocalDate from_date) {
        this.from_date = from_date;
    }

    public LocalDate getTo_date() {
        return to_date;
    }

    public void setTo_date(LocalDate to_date) {
        this.to_date = to_date;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
