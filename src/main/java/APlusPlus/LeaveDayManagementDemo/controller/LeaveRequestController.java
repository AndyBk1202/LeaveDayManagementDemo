package APlusPlus.LeaveDayManagementDemo.controller;

import APlusPlus.LeaveDayManagementDemo.model.LeaveRequest;
import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import APlusPlus.LeaveDayManagementDemo.service.inter.ILeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final ILeaveRequestService leaveRequestService;

    @GetMapping("/view/{id}")
    public ResponseEntity<ApiResponse> getLeaveRequestById(@PathVariable Long id) {
        ApiResponse response = leaveRequestService.getLeaveRequestById(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendLeaveRequest(@RequestHeader(value = "Authorization") String authHeader,
            @RequestBody LeaveRequest leaveRequest) {
        String token = authHeader.substring(7);
        ApiResponse response = leaveRequestService.sendLeaveRequest(token, leaveRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse> updateLeaveRequest(@PathVariable Long id,
            @RequestBody LeaveRequest leaveRequest) {
        ApiResponse response = leaveRequestService.updateLeaveRequest(id, leaveRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> deleteLeaveRequest(@PathVariable Long id) {
        ApiResponse response = leaveRequestService.deleteLeaveRequest(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/view")
    public ResponseEntity<ApiResponse> getAllRequest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = leaveRequestService.getAllRequest(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/accept/{id}")
    public ResponseEntity<ApiResponse> acceptRequest(@PathVariable long id) {
        ApiResponse response = leaveRequestService.handleRequest(id, "ACCEPTED");
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/reject/{id}")
    public ResponseEntity<ApiResponse> rejectRequest(@PathVariable long id) {
        ApiResponse response = leaveRequestService.handleRequest(id, "REJECTED");
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/view-by-date-range")
    public ResponseEntity<ApiResponse> getLeaveRequestsByDateRange(@RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = leaveRequestService.getLeaveRequestsByDateRange(startDate, endDate, pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/view-by-user-id/{userId}")
    public ResponseEntity<ApiResponse> getLeaveRequestsByUserId(@PathVariable long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = leaveRequestService.getLeaveRequestsByUserId(userId, pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/employee/view")
    public ResponseEntity<ApiResponse> getLeaveRequestsByCurrentUser(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = leaveRequestService.getLeaveRequestByCurrentEmployee(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("employee/view-by-date-range")
    public ResponseEntity<ApiResponse> getLeaveRequestsByCurrentUser(@RequestParam LocalDate startDate,
                                                                     @RequestParam LocalDate endDate,
                                                                     @RequestParam(defaultValue = "0") int page,
                                                                     @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = leaveRequestService.getLeaveRequestByCurrentEmployeeAndDateRange(startDate, endDate, pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
