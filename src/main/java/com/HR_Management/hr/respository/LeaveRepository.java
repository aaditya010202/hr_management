package com.HR_Management.hr.respository;

import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.entities.Leave;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, String> {

    List<Leave> findAllByEmployee(Employee emp);
}
