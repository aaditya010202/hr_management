package com.HR_Management.hr.DTO.Request;

import java.util.Date;

public class LeaveRequestDTO {
    String empId;
    String description;
    String type;
    Date from_date;
    Date to_date;

    public LeaveRequestDTO() {
    }

    public LeaveRequestDTO(String empId, String description, String type, Date from_date, Date to_date) {
        this.empId = empId;
        this.description = description;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getFrom_date() {
        return from_date;
    }

    public void setFrom_date(Date from_date) {
        this.from_date = from_date;
    }

    public Date getTo_date() {
        return to_date;
    }

    public void setTo_date(Date to_date) {
        this.to_date = to_date;
    }
}
