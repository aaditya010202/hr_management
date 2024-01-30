package com.HR_Management.hr.services;

import com.HR_Management.hr.DTO.Response.EmployeeResponseDTO;
import com.HR_Management.hr.DTO.Request.EmployeeRequestDTO;
import com.HR_Management.hr.common.CommonUtils;
import com.HR_Management.hr.controller.EmployeeController;
import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.entities.Leave;
import com.HR_Management.hr.entities.Project;
import com.HR_Management.hr.respository.EmployeeRepository;
import com.HR_Management.hr.respository.LeaveRepository;
import com.HR_Management.hr.respository.ProjectRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EmployeeServices {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private ProjectServices projectServices;

    @Transactional
    public List<Employee> saveEmployeeData(List<EmployeeRequestDTO> employeeList, EmployeeController.Band band, EmployeeController.DesignationValues designationValues) {
        String emailRegexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return employeeList.stream().map(employee -> {
            Employee employee1= new Employee();
            employee1.setEmpId(CommonUtils.getUUID());
            employee1.setName(employee.getName());

            if(Pattern.matches(emailRegexPattern,employee.getEmail()))
            {
                employee1.setEmail(employee.getEmail());
            }else {
                throw new RuntimeException("email is invalid.");
            }

            if(Objects.equals(band.toString(), "L1")){
                employee1.setSalary(50000f);
            } else if (Objects.equals(band.toString(), "L2")) {
                employee1.setSalary(100000f);
            }
            else if(Objects.equals(band.toString(), "L3"))
            {
                employee1.setSalary(200000f);
            }
            else {
                employee1.setSalary(employee.getSalary());
            }
            employee1.setLeaves(employee.getLeaves());
            employee1.setJoiningDate(employee.getJoiningDate());
            employee1.setDepartment(employee.getDepartment());
            employee1.setDesignation(designationValues.toString());
            employee1.setAddress(employee.getAddress());
            employee1.setBand(band.toString());
            return employeeRepository.save(employee1);
        }).collect(Collectors.toList());
    }

    public List<EmployeeResponseDTO> getEmployees() {
        return employeeRepository.findAll().stream().map(this::convertEntityToDtoResponse).collect(Collectors.toList());
    }

    private EmployeeResponseDTO convertEntityToDtoResponse(Employee employee)
    {
        EmployeeResponseDTO employeeResponseDTO =new EmployeeResponseDTO();
        employeeResponseDTO.setEmpId(employee.getEmpId());
        employeeResponseDTO.setName(employee.getName());
        employeeResponseDTO.setEmail(employee.getEmail());
        employeeResponseDTO.setSalary(employee.getSalary());
        employeeResponseDTO.setLeaves(employee.getLeaves());
        employeeResponseDTO.setDepartment(employee.getDepartment());
        employeeResponseDTO.setDesignation(employee.getDesignation());
        employeeResponseDTO.setProjectList(employee.getProjects());
        return employeeResponseDTO;
    }

    public Employee getEmployeeById(String empId) {
        return employeeRepository.findByEmpId(empId);
    }

    @Transactional
    public void deleteEmployeeDesignation(String designation) {
        List<Employee> employee= employeeRepository.findAll().stream().filter(employee1 ->
                Objects.equals(employee1.getDesignation(), designation)).toList();

        List<String> EmployeeIds=employee.stream().map(Employee::getEmpId).toList();

        EmployeeIds.stream().peek(id->{
            leaveRepository.deleteAll(leaveRepository.findAll().stream().filter(leave -> Objects.equals(leave.getEmpId(), id)).toList());
        }).collect(Collectors.toList());

        employee.stream().peek(employee1 -> {
            employeeRepository.delete(employee1);
        }).collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByDesignationDesc(String designation)
    {
        return employeeRepository.findAllByDesignationOrderBySalaryDesc(designation);
    }

    public void deleteEmployeesById(String empId)
    {
        Optional<Employee> currentEmployee=employeeRepository.findById(empId);

        if(currentEmployee.isPresent())
        {
            Employee emp=currentEmployee.get();
            List<Project> projectList=emp.getProjects();
            projectList.stream().peek(project -> {
                project.getEmployees().remove(emp);
                projectRepository.save(project);
                emp.getProjects().remove(project);
                employeeRepository.save(emp);
            }).collect(Collectors.toList());

            List<Leave> leaves=leaveRepository.findAll().stream().filter(leave -> Objects.equals(leave.getEmpId(), emp.getEmpId())).toList();
            leaveRepository.deleteAllById(leaves.stream().map(Leave::getId).toList());
            employeeRepository.deleteById(empId);
        }
    }

    public Employee updateEmployeeById(String empId, Employee employeeData)
    {
            Optional<Employee> optionalEmployee = employeeRepository.findById(empId);
            if(optionalEmployee.isPresent()) {
                Employee existingEmployee = optionalEmployee.get();
                existingEmployee.setEmail(employeeData.getEmail() != null ? employeeData.getEmail() : existingEmployee.getEmail());
                existingEmployee.setDepartment(employeeData.getDepartment() != null ? employeeData.getDepartment() : existingEmployee.getDepartment());
                existingEmployee.setName(employeeData.getName() != null ? employeeData.getName() : existingEmployee.getName());
                existingEmployee.setAddress(employeeData.getAddress() != null ? employeeData.getAddress() : existingEmployee.getAddress());
                existingEmployee.setDesignation(employeeData.getDesignation() != null ? employeeData.getDesignation() : existingEmployee.getDesignation());
                existingEmployee.setJoiningDate(employeeData.getJoiningDate() != null ? employeeData.getJoiningDate() : existingEmployee.getJoiningDate());
                existingEmployee.setSalary(employeeData.getSalary() != null ? employeeData.getSalary() : existingEmployee.getSalary());
                existingEmployee.setLeaves(employeeData.getLeaves() != null ? employeeData.getLeaves() : existingEmployee.getLeaves());

                if (employeeData.getProjects() != null) {
                    var projects = new ArrayList<>(projectRepository.findAllById(employeeData.getProjects().stream().map(Project::getId).toList()));
                    List<Project> existingProjects = existingEmployee.getProjects();
                    projects.stream().peek(project -> {
                        existingProjects.add(project);
                        List<Employee> existingEmployees = project.getEmployees();
                        existingEmployees.add(existingEmployee);
                        project.setEmployees(existingEmployees);
                    }).collect(Collectors.toList());
                    existingEmployee.setProjects(existingProjects);
                }
                return employeeRepository.save(existingEmployee);
            }
            else return null;
    }
}

