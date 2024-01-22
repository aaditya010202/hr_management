package com.HR_Management.hr.services;

import com.HR_Management.hr.common.CommonUtils;
import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.entities.Project;
import com.HR_Management.hr.respository.EmployeeRepository;
import com.HR_Management.hr.respository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServices {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    public List<Project> saveProjectsData(List<Project> projectList) {

        projectList.forEach(project -> {project.setP_id(CommonUtils.getUUID());});
        return projectRepository.saveAll(projectList);
    }

    public List<Project> getProjects(){
        return projectRepository.findAll();
    }

    public Project getProjectById(String p_id) {
        Project project= projectRepository.findById(p_id).orElseThrow(RuntimeException::new);
//        project.setEmployees(getListOfEmployeeFromProjectId(p_id));
        return project;
    }
    public Project updateProjectsById(String p_id, Project projectDetails) {
        Optional<Project> project = projectRepository.findById(p_id);

        List<Employee> emp=employeeRepository.findAllById(projectDetails.getEmployees().stream().map(Employee::getEmpId).toList());
        if (project.isPresent()) {
            Project existingProject = project.get();
            existingProject.setName(projectDetails.getName()!=null?projectDetails.getName():existingProject.getName());
            existingProject.setDescription(projectDetails.getDescription()!=null?projectDetails.getDescription():existingProject.getDescription());
//            existingProject.setEmployees(projectDetails.getEmployees()!=null?projectDetails.getEmployees():existingProject.getEmployees());
            existingProject.setEmployees(emp);
            for(Employee e: emp){
                e.getProjects().add(existingProject);
                employeeRepository.save(e);
            }
//            employeeRepository.saveAll(emp);
            return projectRepository.save(existingProject);
        }
        return null;
    }
    @Transactional
    public void deleteProjectById(String p_id) {
        projectRepository.deleteById(p_id);
    }
    @Transactional
    public void deleteProjectByName(String name) {
        projectRepository.deleteByName(name);
    }
    public List<Employee> getListOfEmployeeFromProjectId(String pId){
        return employeeRepository.findAllById(projectRepository.findEmployeeByProjectId(pId));
    }
}
