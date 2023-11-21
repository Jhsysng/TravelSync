package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Tour;
import com.uhban.travelsync.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findAllByUser(User user);
}
