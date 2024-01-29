package com.HR_Management.hr.controller;

import com.HR_Management.hr.DTO.Response.EmployeeResponseDTO;
import com.HR_Management.hr.DTO.Request.EmployeeRequestDTO;
import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.services.EmployeeServices;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employees")
@RestController
/*@RequestMapping("/employee")*/
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

    @GetMapping("")
    @Operation(summary = "Get all employees", description = "Get a list of all the employees")

    public List<EmployeeResponseDTO> getEmployees(){
        return employeeServices.getEmployees();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an employee by id", description = "Enter the employee id to get the employee")
    public Employee getEmployeeById(@PathVariable String id){
        return employeeServices.getEmployeeById(id);
    }

    @GetMapping("/designation/{designation}")
    @Operation(summary = "Get employees by designation", description = "Get all the employees based on the designation")
    public List<Employee> getEmployeesByDesignation(@PathVariable String designation)
    {
        return employeeServices.getEmployeesByDesignationDesc(designation);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update the employee by id", description = "Update the details of employee by id(edit details or add projects)")
    public Employee updateEmployeeById(
            @PathVariable String id,
            @RequestBody Employee employeeData )
    {
        return employeeServices.updateEmployeeById(id, employeeData);
    }

    @PutMapping("")
    @Operation(summary = "Create new employees", description = "Enter the name, email(unique), department, salary(float), designation, total leaves assigned(default: 22), address and joining date.")
    public List<Employee> saveEmployees(
            @RequestBody List<EmployeeRequestDTO> employeeList,
            @RequestParam(name = "Designation") DesignationValues designationValues,
            @RequestParam(name = "Employee Band") Band band){
        return employeeServices.saveEmployeeData(employeeList, band, designationValues);
    }

    @DeleteMapping("/designation/{designation}")
    @Operation(summary = "Delete all employees by designation", description = "Delete all the employees based on the designation of employee.")
    public void deleteEmployeeByDesignation(@PathVariable String designation)
    {
        employeeServices.deleteEmployeeDesignation(designation);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete employee by id", description = "Enter the employee id of the employee to be deleted.")
    public void deleteEmployeeById(@PathVariable String id)
    {
        employeeServices.deleteEmployeesById(id);
    }

    public enum DesignationValues
    {
        HR,
        JuniorDeveloper,
        SeniorDeveloper,
        Manager,
        VP,
        CEO,
        CTO,
        Director,
        SeniorDirector
    }
    public enum DepartmentValues{
        Engineering,
        Sales,
        Marketing,
        Finance
    }

    public enum Band{
        L1,L2,L3, other
    }
}
