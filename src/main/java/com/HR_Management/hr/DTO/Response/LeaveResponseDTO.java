package com.HR_Management.hr.DTO.Response;
import java.time.LocalDate;

public class LeaveResponseDTO {
    String id;
    String empId;
    String description;
    String type;
    LocalDate from_date;
    LocalDate to_date;
    Boolean accepted;
    Long holidayDays;
    String updateBy;

    public LeaveResponseDTO() {
    }

    public LeaveResponseDTO(String id, String empId, String description, String type, LocalDate from_date, LocalDate to_date, Boolean accepted, Long holidayDays, String updateBy) {
        this.id = id;
        this.empId = empId;
        this.description = description;
        this.type = type;
        this.from_date = from_date;
        this.to_date = to_date;
        this.accepted = accepted;
        this.holidayDays = holidayDays;
        this.updateBy=updateBy;
    }


    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Long getHolidayDays() {
        return holidayDays;
    }

    public void setHolidayDays(Long holidayDays) {
        this.holidayDays = holidayDays;
    }
}
