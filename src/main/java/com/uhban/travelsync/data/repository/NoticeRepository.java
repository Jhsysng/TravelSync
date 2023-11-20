package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findAllByGroup(Long groupId);
}
