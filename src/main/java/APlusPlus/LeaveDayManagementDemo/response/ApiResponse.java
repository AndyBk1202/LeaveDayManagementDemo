package APlusPlus.LeaveDayManagementDemo.response;

import APlusPlus.LeaveDayManagementDemo.DTO.LeaveRequestDTO;
import APlusPlus.LeaveDayManagementDemo.DTO.UserDTO;
import APlusPlus.LeaveDayManagementDemo.model.LeaveRequest;
import APlusPlus.LeaveDayManagementDemo.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse{
    int status;
    String message;
    String token;
    String role;
    String expirationTime;

    //Pagination information
    private Integer currentPage;
    private Integer totalPages;
    private Long totalElements;

    User user;
    UserDTO userDTO;
    List<UserDTO> userDTOList;
    List<LeaveRequestDTO> leaveRequestDTOList;
}
