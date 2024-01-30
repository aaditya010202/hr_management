package com.HR_Management.hr.controller;

import com.HR_Management.hr.DTO.Request.LeaveRequestDTO;
import com.HR_Management.hr.DTO.Request.LeaveRequestUpdateDTO;
import com.HR_Management.hr.DTO.Response.LeaveResponseDTO;
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

@RequestMapping("/leaves")
@RestController
public class LeaveController {

    @Autowired
    LeaveServices leaveServices;

    @GetMapping("")
    @Operation(summary = "Get all leaves", description = "Get a list of all applied leaves")

    public List<LeaveResponseDTO> getAllLeaves(){
        return leaveServices.getAllLeaves();
    }

    @GetMapping("/{empId}")
    @Operation(summary = "Get leave by id", description = "Enter the leave id to get the leave")
    public List<Leave> getLeavesByEmployee(@PathVariable String empId)
    {
        return leaveServices.getLeavesByEmployee(empId);
    }

    @GetMapping("/status")
    @Operation(summary = "Get all the leaves based on status(accepted/rejected)")
    @Parameters({@Parameter(in = ParameterIn.HEADER, name = "employeeId", required = true)})
    public List<Leave> showAcceptedRejectedLeaves(@RequestParam(value = "showAcceptReject") Action action, HttpServletRequest httpServletRequest)
    {
        return leaveServices.showAcceptedRejectedLeavesById(action, httpServletRequest.getHeader("employeeId"));
    }
    @PutMapping("")
    @Operation(summary = "Create new leave", description = "Enter employee id, description of leave, type of leave(medical, casual etc.), from date and to date of leave.")
    public Leave saveLeaves(@RequestBody LeaveRequestDTO leave, @RequestParam(value = "leave type") LeaveType leaveType)
    {
        return leaveServices.saveLeaveData(leave, leaveType);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit leave by id", description = "Enter the leave id of the leave to be updated. Enter the employee id of the user which is updating the leave. The employee id should be of the HR or the employee himself who has applied for the leave. Only the HR can change the state of the leave(accepted: true/false).")
    public LeaveResponseDTO updateLeaveById(@PathVariable String id, @RequestBody LeaveRequestUpdateDTO leaveData, @RequestParam(name = "User Id") String currentUserId)
    {
        return leaveServices.updateLeaveById(id, leaveData, currentUserId);
    }
    @PutMapping("/status")
    @Operation(summary = "Accept/Reject leave applications", description = "Enter the employee id of HR only. The json includes the list of all the leave id which needs to be updated.")
    @Parameters({
            @Parameter(in = ParameterIn.HEADER, name = "employeeId", required = true)
    })
    public List<Leave> doActionAtLeaveRequests(
            @RequestBody List<String> ids,
            @RequestParam(value = "action") Action action, HttpServletRequest httpServletRequest)
    {
        return leaveServices.doActionAtLeaveRequests(ids, action, (httpServletRequest.getHeader("employeeId")));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an leave by id", description = "Enter the leave id to delete the leave")
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

    public enum LeaveType{
        Sick,
        Casual
    }
}
