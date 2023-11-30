package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.plan.PlanChangeDto;
import com.uhban.travelsync.data.dto.plan.PlanCreateDto;
import com.uhban.travelsync.data.dto.plan.PlanResponseDto;

import java.util.List;

public interface PlanService {
    List<PlanResponseDto> getPlanList(Long tourId);
    PlanResponseDto savePlan(String userId, PlanCreateDto planCreateDto);
    PlanResponseDto changePlan(String userId, PlanChangeDto planChangeDto);
    void deletePlan(String userId, Long planId);
}
