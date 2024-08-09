package com.watchez.WatchEz.service;

import com.watchez.WatchEz.dto.ApiResponse;
import com.watchez.WatchEz.dto.request.UserCreateRequest;
import com.watchez.WatchEz.dto.response.UserCreateResponse;
import com.watchez.WatchEz.entity.User;
import com.watchez.WatchEz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ApiResponse<UserCreateResponse> createUser(UserCreateRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setEmail(userRequest.getEmail());
        user.setDob(userRequest.getDob());
        user.setGender(userRequest.getGender());
        user.setAvatarUrl(userRequest.getAvatarUrl());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        if (userRepository.existsByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail())) {
            return ApiResponse.<UserCreateResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message("User created failed!")
                    .data(null)
                    .build();
        }
        try {
            User savedUser = userRepository.save(user);
            return ApiResponse.<UserCreateResponse>builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("User created successfully")
                    .data(UserCreateResponse.builder()
                            .username(savedUser.getUsername())
                            .email(savedUser.getEmail())
                            .build())
                    .build();
        } catch (DataIntegrityViolationException e) {
            // Xử lý lỗi khi dữ liệu không hợp lệ, chẳng hạn như trùng lặp hoặc ràng buộc cơ sở dữ liệu
            return new ApiResponse<>(HttpStatus.BAD_REQUEST.value(), "Data integrity violation: " + e.getMessage(), null);
        } catch (Exception e) {
            // Xử lý các lỗi khác
            return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred: " + e.getMessage(), null);
        }
    }

    public ApiResponse<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ApiResponse.<List<User>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get list user successfully")
                .data(users)
                .build();
    }
}
