package APlusPlus.LeaveDayManagementDemo.service.impl;

import APlusPlus.LeaveDayManagementDemo.DTO.LoginRequest;
import APlusPlus.LeaveDayManagementDemo.DTO.UserDTO;
import APlusPlus.LeaveDayManagementDemo.Utils.JwtUtils;
import APlusPlus.LeaveDayManagementDemo.exception.OurException;
import APlusPlus.LeaveDayManagementDemo.model.User;
import APlusPlus.LeaveDayManagementDemo.repository.UserRepository;
import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import APlusPlus.LeaveDayManagementDemo.service.inter.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    UserRepository userRepository;
    JwtUtils jwtUtils;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    @Override
    public ApiResponse register(User user) {
        ApiResponse response = new ApiResponse();
        try{
            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("EMPLOYEE");
                user.setLeaveDays(12);
            }

            if(userRepository.existsByEmail(user.getUsername())){
                throw new OurException("This username already existed, please choose another username");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            response.setStatus(200);
            response.setMessage("Register successfully");
        } catch(OurException e){
            response.setStatus(400);
            response.setMessage(e.getMessage());
        } catch(Exception e){
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        ApiResponse response = new ApiResponse();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new OurException("Username not found"));
            String jwt = jwtUtils.generateToken(user);
            response.setStatus(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setExpirationTime("1 day");
            response.setMessage("Login successfully");
        } catch (BadCredentialsException e) {
                response.setStatus(401);
                response.setMessage("Invalid username or password");
        } catch (OurException e){
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e){
            response.setStatus(500);
            response.setMessage("Error Occurs During User Login: " + e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse updateUser(UserDTO userDTO) {
        ApiResponse response = new ApiResponse();
        try{
            User user = userRepository.findByEmail(userDTO.getEmail()).orElseThrow(() -> new OurException("User not found"));
            if(userDTO.getName() != null) user.setName(userDTO.getName());
            userRepository.save(user);
            response.setStatus(200);
            response.setMessage("Update user successfully");
            response.setUser(user);
        } catch (OurException e){
            response.setStatus(404);
            response.setMessage(e.getMessage());
        } catch (Exception e) {
            response.setStatus(500);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse deleteUser(long id) {
        ApiResponse response = new ApiResponse();
        try {
            if (!userRepository.existsById(id)) {
                throw new OurException("User not found");
            }
            userRepository.deleteById(id);

            response.setStatus(200);
            response.setMessage("User deleted successfully");
        } catch (Exception e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public ApiResponse viewUser(Long id) {

        try {
            User user = userRepository.findById(id).orElse(null);

            if (user == null) {
                throw new OurException("User not found");
            }

            UserDTO userDTO = UserDTO.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .name(user.getName())
                    .build();

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setUserDTO(userDTO);
            apiResponse.setStatus(200);
            apiResponse.setMessage("User found");

            return apiResponse;

        } catch (OurException e) {
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setStatus(404);
            apiResponse.setMessage(e.getMessage());
            return apiResponse;
        }
    }

    @Override
    public ApiResponse viewAllUser(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        List<UserDTO> userDTOList = users.getContent().stream()
                .map(u -> new UserDTO(u.getId(), u.getName(), u.getEmail(), null))
                .collect(Collectors.toList());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCurrentPage(users.getNumber());
        apiResponse.setTotalElements(users.getTotalElements());
        apiResponse.setTotalPages(users.getTotalPages());
        apiResponse.setUserDTOList(userDTOList);
        apiResponse.setStatus(200);
        apiResponse.setMessage("User found");

        return apiResponse;
    }

    @Override
    public User getDetailFromToken(String token) {

        if (jwtUtils.isTokenExpired(token)) {
            throw new OurException("Token is expired");
        }

        String subject = jwtUtils.getSubject(token);
        if (subject == null) {
            throw new OurException("Something went wrong in JWT extraction");
        }

        Optional<User> user = userRepository.findByEmail(subject);
        if (user.isEmpty()) {
            throw new OurException("User not found");
        }

        return user.get();
    }
}
