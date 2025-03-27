package APlusPlus.LeaveDayManagementDemo.service.impl;

import APlusPlus.LeaveDayManagementDemo.DTO.LeaveRequestDTO;
import APlusPlus.LeaveDayManagementDemo.model.LeaveRequest;
import APlusPlus.LeaveDayManagementDemo.model.User;
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

    @Override
    public ApiResponse getAllRequest(Pageable pageable) {
        Page<LeaveRequest> leaveRequests = leaveRequestRepository.findAll(pageable);
        List<LeaveRequestDTO> leaveRequestDTOList = leaveRequests.getContent().stream()
                .map(leaveRequest -> new LeaveRequestDTO(
                        leaveRequest.getId(),
                        leaveRequest.getStartDate(),
                        leaveRequest.getEndDate(),
                        leaveRequest.getReason(),
                        leaveRequest.getStatus(),
                        leaveRequest.getUser().getEmail()))
                .collect(Collectors.toList());
        ApiResponse response = new ApiResponse();
        response.setStatus(200);
        response.setMessage("Fetching all leave requests successfully");
        response.setLeaveRequestDTOList(leaveRequestDTOList);
        return response;
    }

    @Override
    public ApiResponse acceptRequest(long id) {
        return null;
    }

    @Override
    public ApiResponse rejectRequest(long id) {
        return null;
    }
}
