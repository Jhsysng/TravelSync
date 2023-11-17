package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group,Long> {
    @Query("SELECT g FROM Group g JOIN g.groupUsers gu WHERE gu.user.userId = :userId")
    Optional<List<Group>> findByUserId(String userId);
    Optional<Group> findByGroupId(Long groupId);
}
