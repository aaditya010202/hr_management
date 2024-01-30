package com.HR_Management.hr.respository;

import com.HR_Management.hr.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String> {
    List<Employee> deleteByDesignation(String designation);
    Employee findTopByDesignationOrderBySalaryDesc(String designation);
    @Query(value = "SELECT * from employee WHERE designation=:designation ORDER BY salary DESC",nativeQuery = true)
    List<Employee> findAllByDesignationOrderBySalaryDesc(String designation);
    Employee findByEmpId(String empId);

    void deleteById(String empId);

    @Query(value = "select * from employee where emp_id in :list", nativeQuery = true)
    List<Employee> findAllById(List<String> list);

}
