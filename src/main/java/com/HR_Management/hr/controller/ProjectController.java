package com.HR_Management.hr.controller;

import com.HR_Management.hr.entities.Project;
import com.HR_Management.hr.services.ProjectServices;
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
    public List<Project> saveProjects(@RequestBody List<Project> projectList)
    {
        return projectServices.saveProjectsData(projectList);
    }

    @GetMapping("")
    public List<Project> getProjects()
    {
        return projectServices.getProjects();
    }

    @GetMapping("/{p_id}")
    public Project getProjectById(@PathVariable String p_id)
    {
        return projectServices.getProjectById(p_id);
    }

    @PutMapping("/{p_id}")
    public Project updateProjectById(@PathVariable String p_id, @RequestBody Project details)
    {
        return projectServices.updateProjectsById(p_id, details);
    }

    @DeleteMapping("/{p_id}")
    public void deleteProjectById(@PathVariable String p_id)
    {
        projectServices.deleteProjectById(p_id);
    }

}
