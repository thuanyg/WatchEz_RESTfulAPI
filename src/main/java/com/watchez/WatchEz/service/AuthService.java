package com.watchez.WatchEz.service;

import com.watchez.WatchEz.dto.request.AuthRequest;
import com.watchez.WatchEz.exception.AppException;
import com.watchez.WatchEz.exception.ErrorCode;
import com.watchez.WatchEz.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean authenticate(AuthRequest authRequest) {
        var user = userRepository.findByUsernameOrEmail(authRequest.getUsername(), authRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_LOGIN));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        return passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
    }
}
