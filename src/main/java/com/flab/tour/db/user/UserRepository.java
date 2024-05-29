package com.flab.tour.db.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findFirstByEmailAndPasswordOrderByUserIdDesc(String email, String password);

    Optional<UserEntity> findFirstByEmail(String email);

    Optional<UserEntity> findFirstByUserId(String userId);
}
