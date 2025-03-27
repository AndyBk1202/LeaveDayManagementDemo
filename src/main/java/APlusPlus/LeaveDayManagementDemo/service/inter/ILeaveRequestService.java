package APlusPlus.LeaveDayManagementDemo.service.inter;


import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ILeaveRequestService {
    public ApiResponse getAllRequest(Pageable pageable);
    public ApiResponse acceptRequest(long id);
    public ApiResponse rejectRequest(long id);
    public ApiResponse getLeaveRequestsByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
    public ApiResponse getLeaveRequestsByUserId(long userId, Pageable pageable);
}
