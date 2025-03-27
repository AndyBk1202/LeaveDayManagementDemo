package APlusPlus.LeaveDayManagementDemo.service.impl;

import APlusPlus.LeaveDayManagementDemo.DTO.LeaveRequestDTO;
import APlusPlus.LeaveDayManagementDemo.Utils.EmailService;
import APlusPlus.LeaveDayManagementDemo.exception.OurException;
import APlusPlus.LeaveDayManagementDemo.model.LeaveRequest;
import APlusPlus.LeaveDayManagementDemo.repository.LeaveRequestRepository;
import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import APlusPlus.LeaveDayManagementDemo.service.inter.ILeaveRequestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestService implements ILeaveRequestService {
    LeaveRequestRepository leaveRequestRepository;
    EmailService emailService;

    @Override
    public ApiResponse getAllRequest(Pageable pageable) {
        ApiResponse response = new ApiResponse();
        try {
            Page<LeaveRequest> leaveRequests = leaveRequestRepository.findAll(pageable);
            if (leaveRequests.getTotalElements() == 0) {
                throw new OurException("No Leave Requests Found");
            }
            List<LeaveRequestDTO> leaveRequestDTOList = leaveRequests.getContent().stream()
                    .map(leaveRequest -> new LeaveRequestDTO(
                            leaveRequest.getId(),
                            leaveRequest.getStartDate(),
                            leaveRequest.getEndDate(),
                            leaveRequest.getReason(),
                            leaveRequest.getStatus(),
                            leaveRequest.getUser().getEmail()))
                    .collect(Collectors.toList());

            response.setStatus(200);
            response.setMessage("Fetching all leave requests successfully");
            response.setLeaveRequestDTOList(leaveRequestDTOList);
        } catch (OurException e) {
            response.setStatus(200);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Occurs During Fetch Leave Request: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse handleRequest(long id, String status) {
        ApiResponse response = new ApiResponse();
        try {
            LeaveRequest existingRequest = leaveRequestRepository.findById(id).orElse(null);
            if (existingRequest == null) {
                throw new OurException("Leave Request Not Found");
            }
            existingRequest.setStatus(status);
            leaveRequestRepository.save(existingRequest);
            emailService.sendLeaveRequestStatusEmail(existingRequest.getUser().getEmail(), status);

            response.setStatus(200);
            response.setMessage("Leave request has been handled successfully");

        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error Occurs During Accept Leave Request: " + e.getMessage());
        }
        return response;
    }
}
