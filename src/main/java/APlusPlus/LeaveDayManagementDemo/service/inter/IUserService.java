package APlusPlus.LeaveDayManagementDemo.service.inter;

import APlusPlus.LeaveDayManagementDemo.DTO.LoginRequest;
import APlusPlus.LeaveDayManagementDemo.DTO.UserDTO;
import APlusPlus.LeaveDayManagementDemo.model.User;
import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    public ApiResponse register(User user);
    public ApiResponse login(LoginRequest loginRequest);
    public ApiResponse addUser(UserDTO request);
    public ApiResponse updateUser(UserDTO userDTO);
    public ApiResponse deleteUser(long id);
    public ApiResponse viewUser(Long id);
    public ApiResponse viewAllUser(Pageable pageable);
}
