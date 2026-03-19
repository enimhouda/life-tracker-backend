package com.life.tracker.repo;

import com.life.tracker.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
   Optional<UserEntity>  findByEmail(String email);
}
