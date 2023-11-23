package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    List<Plan>  findAllByTour_TourId(Long tourId);
}
