package com.ms.polls.controller;

import com.ms.polls.request.LoginRequest;
import com.ms.polls.request.SignupRequest;
import com.ms.polls.response.ApiResponse;
import com.ms.polls.response.SignupResponse;
import com.ms.polls.service.AuthenticationService;
import com.ms.polls.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody SignupRequest request){
        if(userService.checkUsername(request.getUsername())){
            return new ResponseEntity<>(
                    ApiResponse
                            .builder()
                            .data("")
                            .errors(List.of("Username " + request.getUsername() + " already exists"))
                            .build(), HttpStatus.BAD_REQUEST);
        }

        if(userService.checkEmail(request.getEmail())){
            return new ResponseEntity<>(
                    ApiResponse
                            .builder()
                            .data("")
                            .errors(List.of("Email " + request.getEmail() + " already exists"))
                            .build(), HttpStatus.BAD_REQUEST);
        }

        SignupResponse response = userService.createUser(request);
        return ResponseEntity.ok(ApiResponse
                                    .builder()
                                    .data(response)
                                    .build());
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginRequest request){
        return ResponseEntity.ok(ApiResponse
                .builder()
                .data(authenticationService.authenticate(request))
                .build());
    }

    @PostMapping("/refresh-token")
    @SecurityRequirement(name="BearerAuth")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException {
        authenticationService.refreshToken(request, response);
    }
}
