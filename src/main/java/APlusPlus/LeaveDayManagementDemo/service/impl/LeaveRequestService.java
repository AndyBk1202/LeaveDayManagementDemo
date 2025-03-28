package APlusPlus.LeaveDayManagementDemo.service.impl;

import APlusPlus.LeaveDayManagementDemo.DTO.LeaveRequestDTO;
import APlusPlus.LeaveDayManagementDemo.Utils.EmailService;
import APlusPlus.LeaveDayManagementDemo.exception.OurException;
import APlusPlus.LeaveDayManagementDemo.model.LeaveRequest;
import APlusPlus.LeaveDayManagementDemo.model.User;
import APlusPlus.LeaveDayManagementDemo.repository.LeaveRequestRepository;
import APlusPlus.LeaveDayManagementDemo.repository.UserRepository;
import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import APlusPlus.LeaveDayManagementDemo.service.inter.ILeaveRequestService;
import APlusPlus.LeaveDayManagementDemo.service.inter.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveRequestService implements ILeaveRequestService {
    LeaveRequestRepository leaveRequestRepository;
    EmailService emailService;
    UserRepository userRepository;
    IUserService userService;
    @Override
    public ApiResponse getLeaveRequestById(long id) {
        ApiResponse response = new ApiResponse();
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                    .orElseThrow(() -> new OurException("Leave Request Not Found"));

            // Chuyển đổi trực tiếp thay vì gọi convertToDTO
            LeaveRequestDTO dto = new LeaveRequestDTO(
                    leaveRequest.getId(),
                    leaveRequest.getStartDate(),
                    leaveRequest.getEndDate(),
                    leaveRequest.getReason(),
                    leaveRequest.getStatus(),
                    leaveRequest.getUser().getEmail());

            response.setStatus(200);
            response.setMessage("Leave request fetched successfully");
            response.setLeaveRequestDTOList(Collections.singletonList(dto));
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error fetching leave request: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse deleteLeaveRequest(long id) {
        ApiResponse response = new ApiResponse();
        try {
            LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                    .orElseThrow(() -> new OurException("Leave Request Not Found"));

            if (leaveRequest.getStatus().equalsIgnoreCase("ACCEPTED")) {
                throw new OurException("Leave request has already been accepted and cannot be deleted");
            }
            leaveRequestRepository.delete(leaveRequest);
            response.setStatus(200);
            response.setMessage("Leave request deleted successfully");
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error deleting leave request: " + e.getMessage());
        }
        return response;
    }

    @Override
    @Transactional
    public ApiResponse sendLeaveRequest(String token, LeaveRequest request) {
        ApiResponse response = new ApiResponse();
        try {
            User user = userService.getDetailFromToken(token);
            request.setUser(user);
            request.setStatus("PENDING");
            request.setStartDate(request.getStartDate());
            request.setEndDate(request.getEndDate());
            if (request.getStartDate().isAfter(request.getEndDate())) {
                throw new OurException("Start date cannot be after end date");
            }
            leaveRequestRepository.save(request);

            response.setStatus(200);
            response.setMessage("Leave request submitted successfully");
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error submitting leave request: " + e.getMessage());
        }
        return response;
    }



    @Override
    @Transactional
    public ApiResponse updateLeaveRequest(long id, LeaveRequest updatedRequest) {
        ApiResponse response = new ApiResponse();
        try {
            LeaveRequest existingRequest = leaveRequestRepository.findById(id)
                    .orElseThrow(() -> new OurException("Leave Request Not Found"));

            if (existingRequest.getStatus().equalsIgnoreCase("ACCEPTED")) {
                throw new OurException("Leave request has already been accepted and cannot be updated");
            }
            if (existingRequest.getStatus().equalsIgnoreCase("REJECTED")) {
                throw new OurException("Leave request has already been rejected and cannot be updated");
            }

            existingRequest.setStartDate(updatedRequest.getStartDate());
            existingRequest.setEndDate(updatedRequest.getEndDate());
            existingRequest.setReason(updatedRequest.getReason());
            existingRequest.setStatus(updatedRequest.getStatus());

            leaveRequestRepository.save(existingRequest);
            response.setStatus(200);
            response.setMessage("Leave request updated successfully");
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error updating leave request: " + e.getMessage());
        }
        return response;
    }

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
            response.setCurrentPage(leaveRequests.getNumber());
            response.setTotalElements(leaveRequests.getTotalElements());
            response.setTotalPages(leaveRequests.getTotalPages());
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

    @Override
    public ApiResponse getLeaveRequestsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        ApiResponse response = new ApiResponse();
        try {
            Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.getAllByDateRange(startDate, endDate,
                    pageable);
            List<LeaveRequestDTO> leaveRequestDTOList = leaveRequestPage.getContent().stream()
                    .map(leaveRequest -> new LeaveRequestDTO(
                            leaveRequest.getId(),
                            leaveRequest.getStartDate(),
                            leaveRequest.getEndDate(),
                            leaveRequest.getReason(),
                            leaveRequest.getStatus(),
                            leaveRequest.getUser().getEmail()))
                    .collect(Collectors.toList());
            response.setCurrentPage(leaveRequestPage.getNumber());
            response.setTotalElements(leaveRequestPage.getTotalElements());
            response.setTotalPages(leaveRequestPage.getTotalPages());
            response.setStatus(200);
            response.setMessage("Get all leave requests successfully");
            response.setLeaveRequestDTOList(leaveRequestDTOList);
            return response;
        } catch (OurException e) {
            response.setStatus(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getLeaveRequestsByUserId(long userId, Pageable pageable) {
        ApiResponse response = new ApiResponse();
        try {
            Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.getAllByUserId(userId, pageable);
            List<LeaveRequestDTO> leaveRequestDTOList = leaveRequestPage.getContent().stream()
                    .map(leaveRequest -> new LeaveRequestDTO(
                            leaveRequest.getId(),
                            leaveRequest.getStartDate(),
                            leaveRequest.getEndDate(),
                            leaveRequest.getReason(),
                            leaveRequest.getStatus(),
                            leaveRequest.getUser().getEmail()))
                    .collect(Collectors.toList());
            response.setCurrentPage(leaveRequestPage.getNumber());
            response.setTotalElements(leaveRequestPage.getTotalElements());
            response.setTotalPages(leaveRequestPage.getTotalPages());
            response.setStatus(200);
            response.setMessage("Get all leave requests successfully");
            response.setLeaveRequestDTOList(leaveRequestDTOList);
            return response;
        } catch (OurException e) {
            response.setStatus(400);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getLeaveRequestByCurrentEmployee(Pageable pageable) {
        ApiResponse response = new ApiResponse();
        try {
            // Get current user from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Find the user in the database
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new OurException("Current authenticated user not found"));

            // Get leave requests for this user
            Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.getAllByUserId(user.getId(), pageable);

            // Convert to DTOs
            List<LeaveRequestDTO> leaveRequestDTOList = leaveRequestPage.getContent().stream()
                    .map(leaveRequest -> new LeaveRequestDTO(
                            leaveRequest.getId(),
                            leaveRequest.getStartDate(),
                            leaveRequest.getEndDate(),
                            leaveRequest.getReason(),
                            leaveRequest.getStatus(),
                            leaveRequest.getUser().getEmail()))
                    .collect(Collectors.toList());

            response.setCurrentPage(leaveRequestPage.getNumber());
            response.setTotalElements(leaveRequestPage.getTotalElements());
            response.setTotalPages(leaveRequestPage.getTotalPages());
            response.setStatus(200);
            response.setMessage("Your leave requests retrieved successfully");
            response.setLeaveRequestDTOList(leaveRequestDTOList);
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error retrieving leave requests: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse getLeaveRequestByCurrentEmployeeAndDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        ApiResponse response = new ApiResponse();
        try {
            // Get current user from security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Find the user in the database
            User user = userRepository.findByEmail(username)
                    .orElseThrow(() -> new OurException("Current authenticated user not found"));

            // Get leave requests for this user
            Page<LeaveRequest> leaveRequestPage = leaveRequestRepository.getAllByUserIdAndDateRange(user.getId(),
                    startDate, endDate, pageable);

            // Convert to DTOs
            List<LeaveRequestDTO> leaveRequestDTOList = leaveRequestPage.getContent().stream()
                    .map(leaveRequest -> new LeaveRequestDTO(
                            leaveRequest.getId(),
                            leaveRequest.getStartDate(),
                            leaveRequest.getEndDate(),
                            leaveRequest.getReason(),
                            leaveRequest.getStatus(),
                            leaveRequest.getUser().getEmail()))
                    .collect(Collectors.toList());

            response.setCurrentPage(leaveRequestPage.getNumber());
            response.setTotalElements(leaveRequestPage.getTotalElements());
            response.setTotalPages(leaveRequestPage.getTotalPages());
            response.setStatus(200);
            response.setMessage("Your leave requests retrieved successfully");
            response.setLeaveRequestDTOList(leaveRequestDTOList);
        } catch (OurException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage("Error retrieving leave requests: " + e.getMessage());
        }
        return response;
    }
}
