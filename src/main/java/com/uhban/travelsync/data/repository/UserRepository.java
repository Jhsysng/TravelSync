package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{
    Optional<User> findByUserId(String userId);
    Boolean existsByUserId(String userId);
}
