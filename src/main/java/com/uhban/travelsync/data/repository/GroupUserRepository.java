package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.GroupUserId;
import com.uhban.travelsync.data.entity.Group_User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserRepository extends JpaRepository<Group_User, GroupUserId>{

}
