package com.watchez.WatchEz.controller;

import com.watchez.WatchEz.dto.ApiResponse;
import com.watchez.WatchEz.dto.request.UserCreateRequest;
import com.watchez.WatchEz.dto.request.UserNormalizeRequest;
import com.watchez.WatchEz.dto.response.UserCreateResponse;
import com.watchez.WatchEz.dto.response.UserNormalizeResponse;
import com.watchez.WatchEz.entity.User;
import com.watchez.WatchEz.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
// BASE URL = "https://thuanht.vn"
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

    @GetMapping("/{id}")
    ApiResponse<UserNormalizeResponse> getUserNormalizeByID(@PathVariable String id) {
        return userService.getUserNormalizeByID(id);
    }

    @PostMapping()
    ApiResponse<UserCreateResponse> createUser(@RequestBody UserCreateRequest user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    ApiResponse<UserNormalizeResponse> updateUser(@PathVariable String id, @RequestBody UserNormalizeRequest user){
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deleteUser(@PathVariable String id) {
        return userService.deleteUser(id);
    }
}
