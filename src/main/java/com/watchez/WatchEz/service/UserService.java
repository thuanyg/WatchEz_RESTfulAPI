package com.watchez.WatchEz.service;

import com.watchez.WatchEz.dto.ApiResponse;
import com.watchez.WatchEz.dto.request.UserCreateRequest;
import com.watchez.WatchEz.dto.request.UserNormalizeRequest;
import com.watchez.WatchEz.dto.response.UserCreateResponse;
import com.watchez.WatchEz.dto.response.UserNormalizeResponse;
import com.watchez.WatchEz.entity.User;
import com.watchez.WatchEz.exception.AppException;
import com.watchez.WatchEz.exception.ErrorCode;
import com.watchez.WatchEz.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

        if (userRepository.existsByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail()))
            throw new AppException(ErrorCode.USER_EXISTED);

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
        if (users.isEmpty())
            throw new AppException(ErrorCode.NO_USERS_FOUND);

        return ApiResponse.<List<User>>builder()
                .message("Get list user successfully")
                .data(users)
                .build();

    }

    public ApiResponse<UserNormalizeResponse> getUserNormalizeByID(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserNormalizeResponse userNormalizeResponse = UserNormalizeResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .gender(user.getGender())
                .email(user.getEmail())
                .dob(user.getDob())
                .avatarUrl(user.getAvatarUrl())
                .build();
        return ApiResponse.<UserNormalizeResponse>builder()
                .message("Get user successfully")
                .data(userNormalizeResponse)
                .build();
    }

    public ApiResponse<UserNormalizeResponse> updateUser(String id, UserNormalizeRequest userRequest){
        // Tìm người dùng theo ID hoặc ném ngoại lệ nếu không tìm thấy
        User userToUpdate = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Cập nhật thông tin người dùng
        if (userRequest.getFirstName() != null) {
            userToUpdate.setFirstName(userRequest.getFirstName());
        }
        if (userRequest.getLastName() != null) {
            userToUpdate.setLastName(userRequest.getLastName());
        }
        if (userRequest.getGender() != null) {
            userToUpdate.setGender(userRequest.getGender());
        }
        if (userRequest.getDob() != null) {
            userToUpdate.setDob(userRequest.getDob());
        }
        if (userRequest.getAvatarUrl() != null) {
            userToUpdate.setAvatarUrl(userRequest.getAvatarUrl());
        }

        userRepository.save(userToUpdate);

        // Tạo đối tượng phản hồi sau khi cập nhật thành công
        UserNormalizeResponse userResponse = UserNormalizeResponse.builder()
                .id(userToUpdate.getId())
                .username(userToUpdate.getUsername())
                .firstName(userToUpdate.getFirstName())
                .lastName(userToUpdate.getLastName())
                .gender(userToUpdate.getGender())
                .email(userToUpdate.getEmail())
                .dob(userToUpdate.getDob())
                .avatarUrl(userToUpdate.getAvatarUrl())
                .build();

        // Trả về phản hồi API
        return ApiResponse.<UserNormalizeResponse>builder()
                .message("User updated successfully")
                .data(userResponse)
                .build();

    }

    @Transactional
    public ApiResponse<Void> deleteUser(String id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userRepository.deleteById(id);

        return ApiResponse.<Void>builder()
                .message("User deleted successfully")
                .data(null)
                .build();
    }
}
