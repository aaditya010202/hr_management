package com.HR_Management.hr.controller;

import com.HR_Management.hr.DTO.Request.EmployeeRequestDTO;
import com.HR_Management.hr.DTO.Response.EmployeeResponseDTO;
import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.services.EmployeeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/employees")
@RestController
/*@RequestMapping("/employee")*/
public class EmployeeController {

    @Autowired
    EmployeeServices employeeServices;

//    @GetMapping("")
//    public List<Employee> getEmployees(){
//        return employeeServices.getEmployees();
//    }
    @GetMapping("")
    public List<EmployeeRequestDTO> getEmployees(){
        return employeeServices.getEmployees();
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id){
        return employeeServices.getEmployeeById(id);
    }
    @GetMapping("/designation/{designation}")
    public List<Employee> getEmployeesByDesignation(@PathVariable String designation)
    {
        return employeeServices.getEmployeesByDesignationDesc(designation);
    }

    @PutMapping("/{id}")
    public Employee updateEmployeeById(@PathVariable String id, @RequestBody Employee employeeData )
    {
        return employeeServices.updateEmployeeById(id, employeeData);
    }
    @PutMapping("")
    public List<Employee> saveEmployees(@RequestBody List<EmployeeResponseDTO> employeeList){
        return employeeServices.saveEmployeeData(employeeList);
    }

    @DeleteMapping("/designation/{designation}")
    public void deleteEmployeeByDesignation(@PathVariable String designation)
    {
        employeeServices.deleteEmployeeDesignation(designation);
    }
    @DeleteMapping("/{id}")
    public void deleteEmployeeById(@PathVariable String id)
    {
        employeeServices.deleteEmployeesById(id);
    }

}
