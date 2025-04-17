package com.lms.repository;

import com.lms.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth,Long> {
    Optional<Auth> findByEmail(String email);
    Optional<Auth> findByUsername(String username);
//    @Query(value = "select * from auths where username = :userOrEmail or email = :userOrEmail " , nativeQuery = true)
//    Optional<Auth> findByUsernameOrEmail(@Param("userOrEmail") String userOrEmail);

}
