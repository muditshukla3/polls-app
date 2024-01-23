package com.ms.polls.controller;

import com.ms.polls.dto.UserRecord;
import com.ms.polls.request.SignupRequest;
import com.ms.polls.response.ApiResponse;
import com.ms.polls.response.UserIdentityAvailability;
import com.ms.polls.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/validate/{username}")
    public ResponseEntity<ApiResponse> checkUsername(@PathVariable String username){
        boolean userNameAvailable = userService.checkUsername(username);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(new UserIdentityAvailability(userNameAvailable))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/email/validate/{email}")
    public ResponseEntity<ApiResponse> checkEmail(@PathVariable String email){
        boolean userNameAvailable = userService.checkEmail(email);
        ApiResponse apiResponse = ApiResponse.builder()
                .data(new UserIdentityAvailability(userNameAvailable))
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

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> createProfile(@Valid @RequestBody SignupRequest request){

        if(userService.checkUsername(request.getUsername())){
            return new ResponseEntity(
                            ApiResponse
                                    .builder()
                                    .data("")
                                    .errors(List.of("Username " + request.getUsername() + " already exists"))
                                    .build(), HttpStatus.BAD_REQUEST);
        }

        if(userService.checkEmail(request.getEmail())){
            return new ResponseEntity(
                    ApiResponse
                            .builder()
                            .data("")
                            .errors(List.of("Email " + request.getEmail() + " already exists"))
                            .build(), HttpStatus.BAD_REQUEST);
        }

        UserRecord user = userService.createUser(request);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(user.username()).toUri();

        return ResponseEntity
                .created(location)
                .body(ApiResponse
                        .builder()
                        .data(user)
                        .build());
    }
}
