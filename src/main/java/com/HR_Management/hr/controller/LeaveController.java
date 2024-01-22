package com.HR_Management.hr.controller;

import com.HR_Management.hr.DTO.Request.LeaveRequestDTO;
import com.HR_Management.hr.entities.Leave;
import com.HR_Management.hr.services.LeaveServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/leaves")
@RestController
public class LeaveController {

    @Autowired
    LeaveServices leaveServices;

    @GetMapping("")
    public List<LeaveRequestDTO> getAllLeaves(){
        return leaveServices.getAllLeaves();
    }

    @GetMapping("/{empId}")
    public List<Leave> getLeavesByEmployee(@PathVariable String empId)
    {
        return leaveServices.getLeavesByEmployee(empId);
    }
    @PutMapping("")
    public List<Leave> saveLeaves(@RequestBody List<Leave> leaveList)
    {
        return leaveServices.saveLeaveData(leaveList);
    }

    @PutMapping("/{id}")
    public Optional<Leave> updateLeaveById(@PathVariable String id, @RequestBody Leave leaveData, @RequestParam(name = "User Id") String currentUserId)
    {
        return leaveServices.updateLeaveById(id, leaveData, currentUserId);
    }
    @PutMapping("/status")
    @Operation(summary = "Accept/Reject leave applications", description = "Some description")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "employeeId", required = true)
    })
    public List<Leave> doActionAtLeaveRequests(
            @RequestBody List<String> ids,
            @RequestParam(value = "action") Action action, HttpServletRequest httpServletRequest){
//        return leaveServices.doActionAtLeaveRequests(ids, action, String.fromString(httpServletRequest.getHeader("employeeId")));
        return leaveServices.doActionAtLeaveRequests(ids, action, (httpServletRequest.getHeader("employeeId")));
    }

    @DeleteMapping("/{id}")
    public void deleteLeaveById(@PathVariable String id)
    {
        leaveServices.deleteLeavesById(id);
    }

    public enum Action{
        accept(true), reject(false);

        private boolean b;

        Action(boolean b) {
            this.b = b;
        }

        public boolean val(){
            return this.b;
        }
    }

}
