package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByGroup_GroupId(Long groupId);
}
