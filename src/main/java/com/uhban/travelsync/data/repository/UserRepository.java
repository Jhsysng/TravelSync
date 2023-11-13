package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{

}
