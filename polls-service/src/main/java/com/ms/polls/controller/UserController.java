package com.ms.polls.controller;

import com.ms.polls.response.ApiResponse;
import com.ms.polls.response.UserIdentityAvailability;
import com.ms.polls.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@SecurityRequirements
public class UserController {

    private final UserService userService;

    @GetMapping("/validate/{username}")
    public ResponseEntity<ApiResponse> checkUsername(@PathVariable String username){
        boolean userNameAvailable = userService.checkUsername(username);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(new UserIdentityAvailability(!userNameAvailable))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/email/validate/{email}")
    public ResponseEntity<ApiResponse> checkEmail(@PathVariable String email){
        boolean emailAvailable = userService.checkEmail(email);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(new UserIdentityAvailability(!emailAvailable))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ApiResponse> userProfile(@PathVariable String username){
        ApiResponse apiResponse = ApiResponse.builder()
                .data(userService.userProfile(username))
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
