package com.HR_Management.hr.services;

import com.HR_Management.hr.DTO.Request.LeaveRequestDTO;
import com.HR_Management.hr.DTO.Request.LeaveRequestUpdateDTO;
import com.HR_Management.hr.DTO.Response.LeaveResponseDTO;
import com.HR_Management.hr.common.CommonUtils;
import com.HR_Management.hr.controller.LeaveController;
import com.HR_Management.hr.entities.Employee;
import com.HR_Management.hr.entities.Leave;
import com.HR_Management.hr.respository.EmployeeRepository;
import com.HR_Management.hr.respository.LeaveRepository;
import jakarta.transaction.Transactional;
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

    public List<LeaveResponseDTO> getAllLeaves() {
        return leaveRepository.findAll().stream().map(this::convertEntityToDtoResponse).collect(Collectors.toList());
    }

    private LeaveResponseDTO convertEntityToDtoResponse(Leave leave)
    {
        LeaveResponseDTO leaveResponseDTO =new LeaveResponseDTO();
        leaveResponseDTO.setEmpId(leave.getEmpId());
        leaveResponseDTO.setDescription(leave.getDescription());
        leaveResponseDTO.setType(leave.getType());
        leaveResponseDTO.setFrom_date(leave.getFromDate());
        leaveResponseDTO.setTo_date(leave.getToDate());
        leaveResponseDTO.setAccepted(leave.getAccepted());
        leaveResponseDTO.setHolidayDays(leave.getHolidayDays());
        leaveResponseDTO.setId(leave.getId());
        return leaveResponseDTO;
    }

    @Transactional
    public Leave saveLeaveData(LeaveRequestDTO leave, LeaveController.LeaveType leaveType) {
        Leave leave1=new Leave();
        leave1.setId(CommonUtils.getUUID());
        leave1.setEmpId(leave.getEmpId());
        leave1.setDescription(leave.getDescription());
        leave1.setFromDate(leave.getFrom_date());
        leave1.setToDate(leave.getTo_date());
        leave1.setType(leaveType.toString());
        if (Objects.nonNull(leave.getFrom_date()) && Objects.nonNull(leave.getTo_date()))
        {
//            LocalDate localFromDate = leave.getFrom_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//            LocalDate localToDate = leave.getTo_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localFromDate = leave.getFrom_date();
            LocalDate localToDate = leave.getTo_date();
            Long holidayDays = ChronoUnit.DAYS.between(localFromDate, localToDate);
            leave1.setHolidayDays(holidayDays);
        }
        return leaveRepository.save(leave1);
    }

    public void deleteLeavesById(String id) {
        leaveRepository.deleteById(id);
    }

    public LeaveResponseDTO updateLeaveById(String leaveId, LeaveRequestUpdateDTO leaveData, String currentUserId) {
        Employee currentUser = employeeRepository.findById((currentUserId))
                .orElseThrow(() -> new RuntimeException("user with this id does not exist"));
//        return leaveRepository.findById(leaveId)
//                .map(existingLeave -> {
//                    Assert.isTrue(Objects.equals(existingLeave.getEmpId(), currentUserId) || currentUser.getDesignation().equalsIgnoreCase("HR"), "current user is not authorized to update leave request");
//                    existingLeave.setDescription(leaveData.getDescription());
//                    existingLeave.setType(leaveData.getType());
//                    existingLeave.setUpdatedBy(currentUser);
//                    existingLeave.setFromDate(leaveData.getFrom_date());
//                    existingLeave.setToDate(leaveData.getTo_date());
//                    if (Objects.nonNull(leaveData.getFrom_date()) && Objects.nonNull(leaveData.getTo_date()))
//                    {
////                        LocalDate localFromDate = leaveData.getFrom_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
////                        LocalDate localToDate = leaveData.getTo_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//                        LocalDate localFromDate = leaveData.getFrom_date();
//                        LocalDate localToDate = leaveData.getTo_date();
//                        Long holidayDays = ChronoUnit.DAYS.between(localFromDate, localToDate);
//                        existingLeave.setHolidayDays(holidayDays);
//                    }
//                    if(!currentUser.getDesignation().equalsIgnoreCase("HR"))
//                    {
////                    Assert.isTrue(currentUser.getDesignation().equalsIgnoreCase("HR"),"current user is not allowed to update leave status");
//                    existingLeave.setAccepted(leaveData.getAccepted());
//                    }
//                    return leaveRepository.save(existingLeave);
//
//                });

        Leave existingLeave=leaveRepository.findById(leaveId).get();
        Assert.isTrue(Objects.equals(existingLeave.getEmpId(), currentUserId) || currentUser.getDesignation().equalsIgnoreCase("HR"), "current user is not authorized to update leave request");
        existingLeave.setDescription(leaveData.getDescription()!=null?leaveData.getDescription(): existingLeave.getDescription());
        existingLeave.setType(leaveData.getType()!=null?leaveData.getType():existingLeave.getType());
        existingLeave.setUpdatedBy(currentUser);
        existingLeave.setFromDate(leaveData.getFrom_date()!=null?leaveData.getFrom_date():existingLeave.getFromDate());
        existingLeave.setToDate(leaveData.getTo_date()!=null?leaveData.getTo_date():existingLeave.getToDate());
        if (Objects.nonNull(leaveData.getFrom_date()) && Objects.nonNull(leaveData.getTo_date()))
        {
            LocalDate localFromDate = leaveData.getFrom_date();
            LocalDate localToDate = leaveData.getTo_date();
            Long holidayDays = ChronoUnit.DAYS.between(localFromDate, localToDate);
            existingLeave.setHolidayDays(holidayDays);
        }
        if(!currentUser.getDesignation().equalsIgnoreCase("HR"))
        {
        existingLeave.setAccepted(leaveData.getAccepted());
        }
        leaveRepository.save(existingLeave);
        return convertEntityToDtoResponse(existingLeave);
    }

    public List<Leave> doActionAtLeaveRequests(List<String> ids, LeaveController.Action action, String currentUserId) {
        Employee employee = employeeRepository.findById(currentUserId).orElseThrow(() -> new RuntimeException("wrong header not found"));
        return leaveRepository.findAllById(ids.stream().toList())
                .stream()
                .map(leave -> actionAtSingleLeave(leave, action, employee))
                .toList();
    }

    private Leave actionAtSingleLeave(Leave leave, LeaveController.Action action, Employee currentUser) {
        if(currentUser.getDesignation()!=null) {
            Assert.isTrue(currentUser.getDesignation().equalsIgnoreCase("HR"), "current user is not authorized to accept or reject leave request");
            leave.setAccepted(action.val());
            leave.setUpdatedBy(currentUser);
            return leaveRepository.save(leave);
        }else {
            throw new RuntimeException("User designation does not exist");
        }
    }

    public List<Leave> getLeavesByEmployee(String empId) {
        return leaveRepository.findAll().stream().filter(leave -> Objects.equals(leave.getEmpId(), empId)).toList();
    }

    public List<Leave> showAcceptedRejectedLeavesById(LeaveController.Action action, String employeeId) {
            return leaveRepository.findAll()
                    .stream()
                    .filter(leave -> Objects.equals(leave.getEmpId(), employeeId))
                    .filter(leave -> leave.getAccepted()==action.val())
                    .collect(Collectors.toList());
    }
}
