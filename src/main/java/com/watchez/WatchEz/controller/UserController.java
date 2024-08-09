package com.watchez.WatchEz.controller;

import com.watchez.WatchEz.dto.ApiResponse;
import com.watchez.WatchEz.dto.request.UserCreateRequest;
import com.watchez.WatchEz.dto.response.UserCreateResponse;
import com.watchez.WatchEz.entity.User;
import com.watchez.WatchEz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ApiResponse<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping()
    ApiResponse<UserCreateResponse> createUser(@RequestBody UserCreateRequest user) {
        return userService.createUser(user);
    }
}
