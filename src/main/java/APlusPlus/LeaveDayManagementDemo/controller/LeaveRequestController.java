package APlusPlus.LeaveDayManagementDemo.controller;

import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import APlusPlus.LeaveDayManagementDemo.service.inter.ILeaveRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {
    private final ILeaveRequestService leaveRequestService;

    @GetMapping("/view")
    public ResponseEntity<ApiResponse> getAllRequest(
            @RequestParam (defaultValue = "0") int page,
            @RequestParam (defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        ApiResponse response = leaveRequestService.getAllRequest(pageable);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<ApiResponse> acceptRequest(@PathVariable long id) {
        ApiResponse response = leaveRequestService.handleRequest(id, "ACCEPTED");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<ApiResponse> rejectRequest(@PathVariable long id){
        ApiResponse response = leaveRequestService.handleRequest(id, "REJECTED");
        return ResponseEntity.status(response.getStatus()).body(response);
    }

}
