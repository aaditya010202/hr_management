package com.HR_Management.hr.respository;

import com.HR_Management.hr.entities.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, String> {

}
