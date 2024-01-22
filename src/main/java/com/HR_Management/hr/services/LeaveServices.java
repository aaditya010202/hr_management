package com.HR_Management.hr.services;

import com.HR_Management.hr.DTO.Request.LeaveRequestDTO;
import com.HR_Management.hr.common.CommonUtils;
import com.HR_Management.hr.controller.LeaveController;
import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.entities.Leave;
import com.HR_Management.hr.respository.EmployeeRepository;
import com.HR_Management.hr.respository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeaveServices {
    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public List<LeaveRequestDTO> getAllLeaves() {
        return leaveRepository.findAll().stream().map(this::convertEntityToDtoRequest).collect(Collectors.toList());
    }

    private LeaveRequestDTO convertEntityToDtoRequest(Leave leave)
    {
        LeaveRequestDTO leaveRequestDTO=new LeaveRequestDTO();
        leaveRequestDTO.setEmpId(leave.getEmpId());
        leaveRequestDTO.setDescription(leave.getDescription());
        leaveRequestDTO.setType(leave.getType());
        leaveRequestDTO.setFrom_date(leave.getFromDate());
        leaveRequestDTO.setTo_date(leave.getToDate());
        return leaveRequestDTO;

    }
//    public List<Leave> updateHolidays(List<Leave> leaveList)
//    {
//        leaveList
//                .stream()
//                .peek(leave -> {
//                    if (Objects.nonNull(leave.getFromDate()) && Objects.nonNull(leave.getToDate())) {
//                        LocalDate localFromDate = leave.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                        LocalDate localToDate = leave.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                        Long holidayDays = ChronoUnit.DAYS.between(localFromDate, localToDate);
//                        leave.setHolidayDays(holidayDays);
//                    }
//
//                }).filter(leave -> Objects.nonNull(leave.getFromDate()) && Objects.nonNull(leave.getToDate()))
//                .map(leaveRepository::save)
//                .toList();;
//        return leaveList;
//    }


    public List<Leave> saveLeaveData(List<Leave> leaveList) {
        new Leave().setId(CommonUtils.getUUID());
        leaveList
                .stream()
                .peek(leave -> {
                    if (Objects.nonNull(leave.getFromDate()) && Objects.nonNull(leave.getToDate())) {
                        LocalDate localFromDate = leave.getFromDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        LocalDate localToDate = leave.getToDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        Long holidayDays = ChronoUnit.DAYS.between(localFromDate, localToDate);
                        leave.setHolidayDays(holidayDays);
                    }

                }).filter(leave -> Objects.nonNull(leave.getFromDate()) && Objects.nonNull(leave.getToDate()))
                .map(leaveRepository::save)
//                .peek(
//                        leave -> {
//                    Integer empId=leave.getEmpId();
//                    String leaveId=leave.getId();
//                    leaveRepository.updateMappingColumn(empId,leaveId);
//                }
//                )
                .toList();

//        leaveRepository.findAll().stream().peek(leave -> {
//            Integer empId=leave.getEmpId();
//            String leaveId=leave.getId();
//            leaveRepository.updateMappingColumn(empId,leaveId);
//        });
        return leaveList;
    }

    public void deleteLeavesById(String id) {
        leaveRepository.deleteById(id);
    }

    public Optional<Leave> updateLeaveById(String leaveId, Leave leaveData, String currentUserId) {
        Employee currentUser = employeeRepository.findById((currentUserId))
                .orElseThrow(() -> new RuntimeException("user with this id does not exist"));
        return leaveRepository.findById(leaveId)
                .map(existingLeave -> {

                    Assert.isTrue(existingLeave.getEmpId() == currentUserId || currentUser.getDepartment().equalsIgnoreCase("HR"), "current user is not authorized to update leave request");
//                    Assert.isTrue(existingLeave.getEmployee() == currentUser || currentUser.getDepartment().equalsIgnoreCase("HR"), "current user is not authorized to update leave request");
                    existingLeave.setDescription(leaveData.getDescription());
                    existingLeave.setType(leaveData.getType());
                    existingLeave.setAccepted(leaveData.getAccepted());
                    existingLeave.setUpdatedBy(currentUser);
                    return leaveRepository.save(existingLeave);
                });
    }

    public List<Leave> doActionAtLeaveRequests(List<String> ids, LeaveController.Action action, String currentUserId) {
        Employee employee = employeeRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("wrong header not found"));
//        return leaveRepository.findAllById(ids.stream().map(String::fromString).toList())
        return leaveRepository.findAllById(ids.stream().toList())
                .stream()
                .map(leave -> actionAtSingleLeave(leave, action, employee))
                .toList();
    }

    private Leave actionAtSingleLeave(Leave leave, LeaveController.Action action, Employee currentUser) {
        Assert.isTrue(leave.getEmployee() != currentUser || currentUser.getDepartment().equalsIgnoreCase("HR"), "current user is not authorized to accept or reject leave request");
        leave.setAccepted(action.val());
        leave.setUpdatedBy(currentUser);
        return leaveRepository.save(leave);
    }

    public List<Leave> getLeavesByEmployee(String empId) {
        return leaveRepository.findAll().stream().filter(leave -> leave.getEmpId()==empId).toList();
    }
}
