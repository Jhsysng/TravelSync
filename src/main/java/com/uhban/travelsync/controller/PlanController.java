package com.uhban.travelsync.controller;

import com.uhban.travelsync.data.dto.plan.PlanChangeDto;
import com.uhban.travelsync.data.dto.plan.PlanCreateDto;
import com.uhban.travelsync.data.dto.plan.PlanResponseDto;
import com.uhban.travelsync.service.PlanService;
import com.uhban.travelsync.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class PlanController {
    private final PlanService planService;

    public PlanController(PlanService planService) {
        this.planService = planService;
    }

    @GetMapping("/plan/{tourId}")
    public ResponseEntity<List<PlanResponseDto>> getPlanList(@PathVariable Long tourId){
        log.info("[PlanController] getPlanList tourId : {}", tourId);
        return ResponseEntity.ok(planService.getPlanList(tourId));
    }

    @PostMapping("/plan")
    public ResponseEntity<PlanResponseDto> createPlan(@RequestBody PlanCreateDto planCreateDto){
        log.info("[PlanController] createPlan tourId : {}", planCreateDto.getTourId());
        String userId = SecurityUtils.getCurrentUserId();
        if(userId == null){
            log.error("[PlanController] createPlan 인증된 사용자가 아닙니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(planService.savePlan(userId, planCreateDto));
    }

    @PutMapping("/plan")
    public ResponseEntity<PlanResponseDto> editPlan(@RequestBody PlanChangeDto planChangeDto){
        log.info("[PlanController] editPlan planId : {}", planChangeDto.getPlanId());
        String userId = SecurityUtils.getCurrentUserId();
        if(userId == null){
            log.error("[PlanController] editPlan 인증된 사용자가 아닙니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(planService.changePlan(userId, planChangeDto));
    }
    @DeleteMapping("/plan/{planId}")
    public ResponseEntity<String> deletePlan(@PathVariable Long planId){
        log.info("[PlanController] deletePlan planId : {}", planId);
        String userId = SecurityUtils.getCurrentUserId();
        if(userId == null){
            log.error("[PlanController] deletePlan 인증된 사용자가 아닙니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        planService.deletePlan(userId, planId);
        return ResponseEntity.ok("delete success");
    }


}
