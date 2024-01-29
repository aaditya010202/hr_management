package com.HR_Management.hr.services;

import com.HR_Management.hr.DTO.Request.ProjectRequestDTO;
import com.HR_Management.hr.DTO.Request.ProjectRequestUpdateDTO;
import com.HR_Management.hr.common.CommonUtils;
import com.HR_Management.hr.controller.ProjectController;
import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.entities.Project;
import com.HR_Management.hr.respository.EmployeeRepository;
import com.HR_Management.hr.respository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServices {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    public List<Project> saveProjectsData(List<ProjectRequestDTO> projectList) {
         return projectList.stream().map(project->{
            Project project1=new Project();
            project1.setId(CommonUtils.getUUID());
            project1.setDescription(project.getDescription());
            project1.setName(project.getName());
            project1.setTotalDuration(project.getTotalDuration());
            project1.setTotalCost(project.getTotalCost());
            return projectRepository.save(project1);
        }).collect(Collectors.toList());
    }

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(String id)
    {
        Project project= projectRepository.findById(id).orElseThrow(RuntimeException::new);
//        project.setEmployees(getListOfEmployeeFromProjectId(p_id));
        return project;
    }
    public Project updateProjectsById(String id, ProjectRequestUpdateDTO projectDetails)
    {
        Optional<Project> project = projectRepository.findById(id);
        if(project.isPresent()){
            Project existingProject = project.get();
            existingProject.setName(projectDetails.getName()!=null?projectDetails.getName():existingProject.getName());
            existingProject.setDescription(projectDetails.getDescription()!=null?projectDetails.getDescription():existingProject.getDescription());
            existingProject.setTotalCost(projectDetails.getTotalCost()!=null? projectDetails.getTotalCost():existingProject.getTotalCost());
            existingProject.setTotalDuration(projectDetails.getTotalDuration()!=null?projectDetails.getTotalDuration():existingProject.getTotalDuration());

            if (projectDetails.getEmployees()!=null)
            {
//                List<Employee> emp=employeeRepository.findAllById(projectDetails.getEmployees().stream().map(Employee::getEmpId).toList());
                List<Employee> emp=employeeRepository.findAllById(projectDetails.getEmployees());
                existingProject.setEmployees(emp);
                for(Employee e: new ArrayList<>(emp)){
                    e.getProjects().add(existingProject);
                    employeeRepository.save(e);
                }
            }
            return projectRepository.save(existingProject);
        }
        else return null;
    }
    @Transactional
    public void deleteProjectById(String id)
    {
        Project project=projectRepository.findById(id).get();
        List<Employee> employeeList=project.getEmployees();
        employeeList.stream().peek(employee -> {
            employee.getProjects().remove(project);
            employeeRepository.save(employee);
//            project.getEmployees().remove(employee);
//            projectRepository.save(project);
        }).collect(Collectors.toList());
        project.getEmployees().clear();
        projectRepository.save(project);
        projectRepository.deleteById(id);
    }
    @Transactional
    public void deleteProjectByName(String name)
    {
        projectRepository.deleteByName(name);
    }
    public List<Employee> getListOfEmployeeFromProjectId(String pId)
    {
        return employeeRepository.findAllById(projectRepository.findEmployeeByProjectId(pId));
    }

    public Project assignDeassign(String id, String empId, ProjectController.AssignDeassign assignDeassign) {
        Project project=projectRepository.findById(id).get();
        Employee employee=employeeRepository.findById(empId).get();
        if(Objects.equals(assignDeassign.toString(),"Assign")){
            project.getEmployees().add(employee);
            projectRepository.save(project);
            employee.getProjects().add(project);
            employeeRepository.save(employee);
        }
        else{
            project.getEmployees().remove(employee);
            projectRepository.save(project);
            employee.getProjects().remove(project);
            employeeRepository.save(employee);
        }
        return project;
    }
}
