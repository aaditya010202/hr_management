package com.HR_Management.hr.controller;

import com.HR_Management.hr.DTO.Request.ProjectRequestDTO;
import com.HR_Management.hr.DTO.Request.ProjectRequestUpdateDTO;
import com.HR_Management.hr.entities.Project;
import com.HR_Management.hr.services.ProjectServices;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/projects")
@RestController
public class ProjectController {
    @Autowired
    ProjectServices projectServices;

    @PutMapping("")
    @Operation(summary = "Create a new project", description = "Enter name and description for the creation of project")
    public List<Project> saveProjects(@RequestBody List<ProjectRequestDTO> projectList)
    {
        return projectServices.saveProjectsData(projectList);
    }

    @GetMapping("")
    @Operation(summary = "Get all projects", description = "Get a list of all projects")
    public List<Project> getProjects()
    {
        return projectServices.getProjects();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by id", description = "Enter the project id to get the project")
    public Project getProjectById(@PathVariable String id)
    {
        return projectServices.getProjectById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update project by id", description = "Edit the project id and the updated details. Employees can also be assigned to the projects by adding the employee ids in the json.")
    public Project updateProjectById(@PathVariable String id, @RequestBody ProjectRequestUpdateDTO details)
    {
        return projectServices.updateProjectsById(id, details);
    }

    @PutMapping("/{id}/assign/{empId}")
    @Operation(summary = "Assign/Deassign employees to projects")
    public Project assignDeassignEmployees(@PathVariable String id,@PathVariable String empId, @RequestParam(name = "Assign/DeassignEmployees") AssignDeassign assignDeassign)
    {
        return projectServices.assignDeassign(id,empId, assignDeassign);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project by id", description = "Enter the project id to delete the project.")
    public void deleteProjectById(@PathVariable String id)
    {
        projectServices.deleteProjectById(id);
    }

    public enum AssignDeassign{
        Assign,
        Deassign
    }
}
