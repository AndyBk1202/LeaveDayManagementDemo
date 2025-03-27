package APlusPlus.LeaveDayManagementDemo.controller;

import APlusPlus.LeaveDayManagementDemo.DTO.LoginRequest;
import APlusPlus.LeaveDayManagementDemo.model.User;
import APlusPlus.LeaveDayManagementDemo.response.ApiResponse;
import APlusPlus.LeaveDayManagementDemo.service.impl.UserService;
import APlusPlus.LeaveDayManagementDemo.service.inter.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class  AuthenticationController {
    private final IUserService userService;

    @PostMapping("/register")
    private ResponseEntity<ApiResponse> register(@Valid @RequestBody User user){
        ApiResponse response = userService.register(user);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        ApiResponse response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
