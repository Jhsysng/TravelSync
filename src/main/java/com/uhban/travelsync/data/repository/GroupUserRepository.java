package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Group;
import com.uhban.travelsync.data.entity.GroupUserId;
import com.uhban.travelsync.data.entity.Group_User;
import com.uhban.travelsync.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupUserRepository extends JpaRepository<Group_User, GroupUserId>{

    boolean existsByUser_UserIdAndGroup_GroupId(String userId, Long groupId);
    List<Group_User> findAllByGroup_GroupId(Long groupId);
}
