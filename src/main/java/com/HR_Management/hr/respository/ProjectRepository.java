package com.HR_Management.hr.respository;

import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, String> {

    void deleteByName(String name);

    @Query(value = "select * from  employee_projects where p_id=:pId",nativeQuery = true)
    List<String> findEmployeeByProjectId(String pId);

    Optional<Project> findById(String pId);

    @Query(value = "select * from project where p_id in :list", nativeQuery = true)
    List<Project> findAllById(List<String> list);

    void deleteById(String pId);
}
