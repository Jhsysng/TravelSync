package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.data.dto.plan.PlanChangeDto;
import com.uhban.travelsync.data.dto.plan.PlanCreateDto;
import com.uhban.travelsync.data.dto.plan.PlanResponseDto;
import com.uhban.travelsync.data.entity.Plan;
import com.uhban.travelsync.data.entity.Tour;
import com.uhban.travelsync.data.repository.PlanRepository;
import com.uhban.travelsync.data.repository.TourRepository;
import com.uhban.travelsync.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PlanServiceImpl implements PlanService {
    private final PlanRepository planRepository;
    private final TourRepository tourRepository;

    public PlanServiceImpl(PlanRepository planRepository, TourRepository tourRepository) {
        this.planRepository = planRepository;
        this.tourRepository = tourRepository;
    }

    @Transactional(readOnly = true)
    public List<PlanResponseDto> getPlanList(Long tourId){
        log.info("[PlanService] getPlanList : {}", tourId);
        List<PlanResponseDto> planList = planRepository.findAllByTour_TourId(tourId).stream()
                .map(plan -> new PlanResponseDto(
                        plan.getPlanId(),
                        plan.getTour().getTourId(),
                        plan.getDay(),
                        plan.getTime(),
                        plan.getPlanTitle(),
                        plan.getPlanContent()
                ))
                .collect(Collectors.toList());
        log.info("[PlanService] getPlanList Success : {}", tourId);
        return planList;
    }

    @Transactional
    public PlanResponseDto savePlan(String userId, PlanCreateDto planCreateDto){
        log.info("[PlanService] savePlan tourId : {}", planCreateDto.getTourId());
        Optional<Tour> optionalTour = tourRepository.findById(planCreateDto.getTourId());
        if (optionalTour.isEmpty()) {
            log.error("Tour not found for ID: {}", planCreateDto.getTourId());
            throw new IllegalArgumentException("Tour not found for ID: " + planCreateDto.getTourId());
        }else if(!optionalTour.get().getUser().getUserId().equals(userId)){
            log.error("[PlanService] savePlan 인증된 사용자가 여행의 주인이 아닙니다.");
            throw new IllegalArgumentException("인증된 사용자가 여행의 주인이 아닙니다.");
        }
        Plan plan = planRepository.save(Plan.builder()
                .tour(optionalTour.get())
                .day(planCreateDto.getDay())
                .time(planCreateDto.getTime())
                .planTitle(planCreateDto.getPlanTitle())
                .planContent(planCreateDto.getPlanContent())
                .build());
        log.info("[PlanService] savePlan Success : {}", planCreateDto.getTourId());
        return PlanResponseDto.builder()
                .planId(plan.getPlanId())
                .tourId(plan.getTour().getTourId())
                .day(plan.getDay())
                .time(plan.getTime())
                .planTitle(plan.getPlanTitle())
                .planContent(plan.getPlanContent())
                .build();
    }

    @Transactional
    public PlanResponseDto changePlan(String userId, PlanChangeDto planChangeDto) {
        log.info("[PlanService] changePlan planId : {}", planChangeDto.getPlanId());
        Plan plan = planRepository.findById(planChangeDto.getPlanId())
                .orElseThrow(()->{
                    log.error("[PlanService] changePlan Plan not found for ID: {}", planChangeDto.getPlanId());
                    throw new IllegalArgumentException("Plan not found for ID: " + planChangeDto.getPlanId());
                });
        if (!plan.getTour().getUser().getUserId().equals(userId)) {
            log.error("[PlanService] changePlan 인증된 사용자가 여행의 주인이 아닙니다.");
            throw new IllegalArgumentException("인증된 사용자가 여행의 주인이 아닙니다.");
        }

        Plan changedPlan = planRepository.save(Plan.builder()
                .planId(plan.getPlanId())
                .tour(plan.getTour())
                .day(planChangeDto.getDay())
                .time(planChangeDto.getTime())
                .planTitle(planChangeDto.getPlanTitle())
                .planContent(planChangeDto.getPlanContent())
                .build());
        log.info("[PlanService] changePlan Success : {}", planChangeDto.getPlanId());

        return PlanResponseDto.builder()
                .planId(changedPlan.getPlanId())
                .tourId(changedPlan.getTour().getTourId())
                .day(changedPlan.getDay())
                .time(changedPlan.getTime())
                .planTitle(changedPlan.getPlanTitle())
                .planContent(changedPlan.getPlanContent())
                .build();
    }


    @Transactional
    public void deletePlan(String userId, Long planId){
        log.info("[PlanService] deletePlan planId : {}", planId);
        Optional<Plan> optionalPlan = planRepository.findById(planId);
        if (optionalPlan.isEmpty()) {
            log.error("[PlanService] deletePlan Plan not found for ID: {}", planId);
            throw new IllegalArgumentException("Plan not found for ID: " + planId);
        }else if(!optionalPlan.get().getTour().getUser().getUserId().equals(userId)){
            log.error("[PlanService] deletePlan 인증된 사용자가 여행의 주인이 아닙니다.");
            throw new IllegalArgumentException("인증된 사용자가 여행의 주인이 아닙니다.");
        }
        planRepository.deleteById(planId);
        log.info("[PlanService] deletePlan Success : {}", planId);
    }
}
