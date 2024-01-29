package com.HR_Management.hr.DTO.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimesheetRequestDTO {
    Float duration;
    String project_id;
//    LocalTime entryTime;
//    LocalTime exitTime;

    public TimesheetRequestDTO() {
    }

    public TimesheetRequestDTO( Float duration, String project_id) {
        this.duration = duration;
        this.project_id=project_id;
//        this.entryTime=entryTime;
//        this.exitTime=exitTime;
    }

//    public LocalTime getEntryTime() {
//        return entryTime;
//    }
//
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

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }
}
