package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
