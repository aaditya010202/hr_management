package com.HR_Management.hr.controller;

import com.HR_Management.hr.DTO.Request.TimesheetRequestDTO;
import com.HR_Management.hr.DTO.Response.TimesheetCategorisedResponseDTO;
import com.HR_Management.hr.entities.Timesheet;
import com.HR_Management.hr.respository.TimesheetRepository;
import com.HR_Management.hr.services.TimesheetServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.SqlReturnType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/timesheet")
public class TimesheetController {
    @Autowired
    private TimesheetServices timesheetServices;
    @Autowired
    private TimesheetRepository timesheetRepository;

    @GetMapping("")
    @Operation(summary = "get all the timesheet data")
    public List<Timesheet> getAllTimesheetData(@RequestParam(name = "employee id", required = false) String empId, @RequestParam(name = "project id", required = false) String pId, @RequestParam(name = "date", required = false) LocalDate date)
    {
        return timesheetServices.getData(empId, pId, date);
    }

    @GetMapping("/{empId}")
    @Operation(summary = "get the total working days of the employee")
    public Float getTotalWorkingDaysOfEmp(@PathVariable String empId, @RequestParam(name = "from date") LocalDate from_date, @RequestParam(name = "to date") LocalDate to_date)
    {
        return timesheetServices.getWorkingDaysOfEmployee(empId, from_date, to_date);
    }

    @GetMapping("/projectCost")
    @Operation(summary = "get current total cost of project", description = "Enter the project id to get the total cost spent on the employees for the project till date.")
    public Float getTotalProjectCost(@RequestParam(name = "project id") String pId)
    {
        return timesheetServices.getCurrentCostOfProject(pId);
    }

    @GetMapping("/project/{pId}")
    @Operation(summary = "Get the total hours spent by all the employees on a project", description = "Enter the project id, from date and to date to get the total hours spent on the project for a particular duration of dates.")
    public Float getTotalHoursOfProject(@PathVariable String pId, @RequestParam(name = "from date") LocalDate from_date, @RequestParam(name = "to date") LocalDate to_date)
    {
        return timesheetServices.getHoursOfProject(pId, from_date, to_date);
    }

    @GetMapping("/categorisedTimesheet/{pId}")
    @Operation(summary = "Get count,cost and time of project based on employee band", description = "Enter the project id and select the employee band.")
    public TimesheetCategorisedResponseDTO getCategorisedResponse(@PathVariable String pId, @RequestParam(name = "employee band") EmployeeController.Band band)
    {
        return timesheetServices.getCategorisedProjectResponse(pId, band);
    }

    @PutMapping("")
    @Operation(summary = "Employee enters the hours worked on a project daily.", description = "Enter the employee id, project id and duration of time worked on the project that day.")
    public Timesheet saveNewEntry(@RequestParam(name = "employee id") String emp_id, @RequestBody TimesheetRequestDTO data)
    {
        return timesheetServices.saveNewTimesheetEntry(emp_id,data);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "update timesheet by id", description = "Timesheet can be updated on the same day by the employee. Admin can update the timesheet anytime.")
    public Timesheet updateTimesheetById(@PathVariable String id,@RequestParam(name = "User id")String empId, @RequestBody TimesheetRequestDTO data)
    {
        return timesheetServices.updateTimesheet(id,empId, data);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "delete timesheet by id", description = "Only the HR has the permission to delete the timesheet, hence enter the employee id of HR.")
    public void deleteTimesheet(@PathVariable String id, @RequestParam(name = "Admin user id") String empId)
    {
        timesheetServices.deleteTimesheetById(id, empId);
    }
}
