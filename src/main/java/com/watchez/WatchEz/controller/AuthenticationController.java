package com.watchez.WatchEz.controller;

import com.watchez.WatchEz.dto.ApiResponse;
import com.watchez.WatchEz.dto.request.AuthRequest;
import com.watchez.WatchEz.dto.response.AuthResponse;
import com.watchez.WatchEz.exception.ErrorCode;
import com.watchez.WatchEz.service.AuthService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

    AuthService authService;

    public AuthenticationController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    ApiResponse<AuthResponse> loginAuthenticate(@RequestBody AuthRequest authRequest) {
        AuthResponse authResponse = authService.authenticate(authRequest);
        return ApiResponse.<AuthResponse>builder()
                .message("Success")
                .data(authResponse)
                .build();
    }
}
