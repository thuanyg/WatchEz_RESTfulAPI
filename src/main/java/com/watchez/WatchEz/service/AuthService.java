package com.watchez.WatchEz.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.watchez.WatchEz.dto.request.AuthRequest;
import com.watchez.WatchEz.dto.response.AuthResponse;
import com.watchez.WatchEz.exception.AppException;
import com.watchez.WatchEz.exception.ErrorCode;
import com.watchez.WatchEz.repository.UserRepository;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    @NonFinal
    public static String SIGNER_KEY = "b15cc2a6a69c57e73b62faad7a59d3f70a32a3f17185ab6c7a499208ff9aaea9";

    UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public AuthResponse authenticate(AuthRequest authRequest) {
        var user = userRepository.findByUsernameOrEmail(authRequest.getUsername(), authRequest.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_LOGIN));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean isAuthenticated = passwordEncoder.matches(authRequest.getPassword(), user.getPassword());
        if (!isAuthenticated) {
            return AuthResponse.builder()
                    .success(false)
                    .token(null)
                    .build();
        }

        String token = generateToken(authRequest.getUsername());
        return AuthResponse.builder()
                .success(true)
                .token(token)
                .build();
    }


    private String generateToken(String username) {

        String userID = userRepository.findByUsernameOrEmail(username, username).get().getId();

        // Create Header + Payload

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("WatchEz")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli()
                ))
                .claim("userid", userID)
                .build();


        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWSObject jwsObject = new JWSObject(header, payload);

        // Sign
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
