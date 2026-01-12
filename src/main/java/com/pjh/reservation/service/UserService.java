package com.pjh.reservation.service;

import com.pjh.reservation.dto.requset.CreateUserRequest;
import com.pjh.reservation.dto.requset.UpdateUserRequest;
import com.pjh.reservation.dto.response.UserResponse;
import com.pjh.reservation.entity.User;
import com.pjh.reservation.global.exception.UserErrorCode;
import com.pjh.reservation.global.exception.UserException;
import com.pjh.reservation.repository.UesrRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("legacyUserService")
@Transactional
public class UserService {

    private final UesrRepository userRepository;

    public UserService(UesrRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponse create(CreateUserRequest req) {

        if(userRepository.existsByLoginId(req.loginId())) {
            throw UserException.of(UserErrorCode.USER_DUPLICATED,
                    "이미 존재하는 아이디입니다. loginId=" + req.loginId());
        }
        User user = new User(
                req.loginId(),
                req.password(),
                req.name(),
                req.phone(),
                req.email()
        );

        User saveUser = userRepository.save(user);

        return UserResponse.from(saveUser);
    }


    @Transactional(readOnly = true)
    public UserResponse get(String loginId) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> UserException.of(UserErrorCode.USER_NOT_FOUND,
                        "사용자를 찾을 수 없습니다. loginId=" + loginId));

        return UserResponse.from(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponse> list() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }

    public UserResponse update(String loginId, UpdateUserRequest req) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> UserException.of(UserErrorCode.USER_NOT_FOUND,
                        "사용자를 찾을 수 없습니다. loginId=" + loginId));

        user.update(req.password(), req.name(), req.phone(), req.email());

        return UserResponse.from(user);
    }

    public void delete(String loginId) {

        User user = userRepository.findByLoginId(loginId)
                .orElseThrow(() -> UserException.of(UserErrorCode.USER_NOT_FOUND,
                        "사용자를 찾을 수 없습니다. loginId=" + loginId));

        userRepository.delete(user);
    }

}
