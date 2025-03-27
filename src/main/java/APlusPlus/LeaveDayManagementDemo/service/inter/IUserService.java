package APlusPlus.LeaveDayManagementDemo.service.inter;

import APlusPlus.LeaveDayManagementDemo.DTO.UserDTO;
import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    public ApiResponse addUser(UserDTO request);
    public ApiResponse updateUser(UserDTO userDTO);
    public ApiResponse deleteUser(long id);
    public ApiResponse viewUser(Long id);
    public ApiResponse viewAllUser(Pageable pageable);
}
