package com.HR_Management.hr.services;

import com.HR_Management.hr.DTO.Request.TimesheetRequestDTO;
import com.HR_Management.hr.DTO.Response.TimesheetCategorisedResponseDTO;
import com.HR_Management.hr.common.CommonUtils;
import com.HR_Management.hr.controller.EmployeeController;
import com.HR_Management.hr.controller.LeaveController;
import com.HR_Management.hr.entities.Timesheet;
import com.HR_Management.hr.respository.EmployeeRepository;
import com.HR_Management.hr.respository.ProjectRepository;
import com.HR_Management.hr.respository.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TimesheetServices {
    @Autowired
    TimesheetRepository timesheetRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    public List<Timesheet> getData(String empId, String pId, LocalDate date) {
        return timesheetRepository.findAll()
                .stream()
                .filter(timesheet -> empId==null || Objects.equals(timesheet.getEmp_id(), empId))
                .filter(timesheet -> pId==null || Objects.equals(timesheet.getP_id(), pId))
                .filter(timesheet -> date==null || Objects.equals(timesheet.getDate(),date))
                .toList();
    }
    public Timesheet saveNewTimesheetEntry(String empId, String pId, TimesheetRequestDTO data) {
        if(projectRepository.findById(pId).get().getEmployees().contains(employeeRepository.findByEmpId(empId))) {
            Timesheet timesheet = new Timesheet();
            timesheet.setId(CommonUtils.getUUID());
            timesheet.setEmp_id(empId);
            timesheet.setHours(data.getDuration());
            timesheet.setDate(LocalDate.now());
            timesheet.setP_id(pId);
            return timesheetRepository.save(timesheet);
        }
        return null;
    }

    public Float getWorkingDaysOfEmployee(String empId, LocalDate fromDate, LocalDate toDate) {
        List<Timesheet> employeeTimesheet= timesheetRepository.findAll().stream().filter(timesheet -> Objects.equals(timesheet.getEmp_id(),empId)).toList();
        List<LocalDate> employeeDates = employeeTimesheet
                .stream()
                .map(Timesheet::getDate)
                .filter(date ->  !date.isBefore(fromDate) && !date.isAfter(toDate))
                .toList();
        return employeeDates.size()*1.0f;
    }

    public Float getHoursOfProject(String pId, LocalDate fromDate, LocalDate toDate) {
        return timesheetRepository.findAll()
                .stream()
                .filter(timesheet -> Objects.equals(timesheet.getP_id(), pId))
                .filter(timesheet -> !timesheet.getDate().isBefore(fromDate) && !timesheet.getDate().isAfter(toDate))
                .map(Timesheet::getHours)
                .reduce(0.0f, Float::sum);
    }

    public Float getCurrentCostOfProject(String pId) {
        return projectRepository.findById(pId).get().getEmployees().stream().map(employee -> {
            String band=employee.getBand();
            Float hours=timesheetRepository.findAll()
                    .stream()
                    .filter(timesheet ->Objects.equals(timesheet.getP_id(), pId))
                    .filter(timesheet -> Objects.equals(timesheet.getEmp_id(),employee.getEmpId()))
                    .map(Timesheet::getHours)
                    .reduce(0.0f, Float::sum);

            if(band==null) return employee.getSalary()*hours/30f/9f;
            if(band.equals("L1")){
                return 50000f*hours/30f/9f;
            } else if (band.equals("L2")){
                return 100000f*hours/30f/9f;
            }else if(band.equals("L3")){
                return 200000f*hours/30f/9f;
            }
            else return employee.getSalary()*hours;
        }).reduce(0.0f, Float::sum);
    }

    public TimesheetCategorisedResponseDTO getCategorisedProjectResponse(String pId, EmployeeController.Band band) {
        TimesheetCategorisedResponseDTO responseProjectDTO=new TimesheetCategorisedResponseDTO();
        timesheetRepository.findAll()
                .stream()
                .filter(timesheet -> Objects.equals(timesheet.getP_id(), pId))
                .filter(timesheet ->
                        Objects.equals(employeeRepository.findById(timesheet.getEmp_id()).get().getBand(), band.toString()))
                .peek(timesheet -> {
                    responseProjectDTO.setCost(responseProjectDTO.getCost()+timesheet.getHours()*employeeRepository.findById(timesheet.getEmp_id()).get().getSalary()/30f/9f);
                    responseProjectDTO.setCount(responseProjectDTO.getCount()+1);
                    responseProjectDTO.setHours(responseProjectDTO.getHours()+timesheet.getHours());
                }).collect(Collectors.toList());
        return responseProjectDTO;
    }

    public void deleteTimesheetById(String id, String empId) {
        if(Objects.equals(employeeRepository.findById(empId).get().getDesignation(), "HR"))
        {
            timesheetRepository.deleteById(id);
        }
        else throw new RuntimeException("user is not authorized to delete the timesheet");
    }

    public Timesheet updateTimesheet(String id, String empId, TimesheetRequestDTO data) {
        Timesheet currentTimesheet=timesheetRepository.findById(id).get();
        if(Objects.equals(currentTimesheet.getDate(), LocalDate.now()) || Objects.equals(employeeRepository.findById(empId).get().getDesignation(), "HR"))
        {
            currentTimesheet.setHours(data.getDuration());
            currentTimesheet.setP_id(data.getProject_id());
            return timesheetRepository.save(currentTimesheet);
        }
        else throw new RuntimeException("You cannot edit the timesheet  as the date has passed.");
    }
}
