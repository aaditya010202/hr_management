package com.HR_Management.hr.DTO.Request;

import java.time.LocalDate;
import java.util.Date;

public class LeaveRequestDTO {
    String empId;
    String description;

    LocalDate from_date;
    LocalDate to_date;


    public LeaveRequestDTO() {
    }

    public LeaveRequestDTO(String empId, String description,  LocalDate from_date, LocalDate to_date) {
        this.empId = empId;
        this.description = description;
        this.from_date = from_date;
        this.to_date = to_date;
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
}
