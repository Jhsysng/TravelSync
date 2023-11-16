package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TourRepository extends JpaRepository<Tour, Long> {
}
