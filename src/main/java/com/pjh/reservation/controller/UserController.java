package com.pjh.reservation.controller;

import com.pjh.reservation.dto.requset.CreateUserRequest;
import com.pjh.reservation.dto.requset.UpdateUserRequest;
import com.pjh.reservation.dto.response.UserResponse;
import com.pjh.reservation.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse create(@Valid @RequestBody CreateUserRequest req) {
        return userService.create(req);
    }

    @GetMapping
    public List<UserResponse> list() {
        return userService.list();
    }

    @GetMapping("/{loginId}")
    public UserResponse get(@PathVariable String loginId) {
        return userService.get(loginId);
    }

    @PutMapping("/{loginId}")
    public UserResponse update(@PathVariable String loginId,
                               @Valid @RequestBody UpdateUserRequest req) {
        return userService.update(loginId, req);
    }

    @DeleteMapping("/{loginId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String loginId) {
        userService.delete(loginId);
    }

}
