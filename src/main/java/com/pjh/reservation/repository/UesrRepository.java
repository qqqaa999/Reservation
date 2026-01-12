package com.pjh.reservation.repository;

import com.pjh.reservation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UesrRepository extends JpaRepository<User,Long> {

    // 아이디 중복 체크
    boolean existsByLoginId(String loginId);

    Optional<User> findByLoginId(String loginId);


}
