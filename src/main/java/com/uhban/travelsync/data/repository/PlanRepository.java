package com.uhban.travelsync.data.repository;

import com.uhban.travelsync.data.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
