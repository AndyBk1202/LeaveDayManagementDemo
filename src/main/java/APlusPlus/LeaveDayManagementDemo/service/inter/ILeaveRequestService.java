package APlusPlus.LeaveDayManagementDemo.service.inter;


import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import org.springframework.data.domain.Pageable;

public interface ILeaveRequestService {
    public ApiResponse getAllRequest(Pageable pageable);
    public ApiResponse handleRequest(long id, String status);
}
