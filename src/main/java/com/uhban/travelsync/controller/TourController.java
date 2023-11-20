package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.tour.TourCreateDto;
import com.uhban.travelsync.data.dto.tour.TourResponseDto;
import com.uhban.travelsync.service.GroupService;
import com.uhban.travelsync.service.TourService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class TourController {

    private final TourService tourService;
    private final GroupService groupService;

    public TourController(TourService tourService, GroupService groupService) {
        this.tourService = tourService;
        this.groupService = groupService;
    }
    @GetMapping("/tour/{groupId}")
    public ResponseEntity<TourResponseDto> getGroupTour(@PathVariable Long groupId) {
        log.info("[TourController] getGroupTour groupId : {}", groupId);
        Long tourId = groupService.getGroup(groupId).getTourId();
        if(tourId != null){
            log.info("[TourController] getGroupTour tourId : {}", tourId);
            return ResponseEntity.ok(tourService.getTour(tourId));
        }else{
            log.error("[TourController] getGroupTour 그룹에 여행이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/tour/list/{userId}")
    public ResponseEntity<List<TourResponseDto>> getTourList(@PathVariable String userId) {
        log.info("[TourController] getTourList userId : {}", userId);
        return ResponseEntity.ok(tourService.getTourList(userId));
    }
    @PostMapping("/tour/create")
    public ResponseEntity<TourResponseDto> createTour(@RequestBody TourCreateDto tourCreateDto) {
        log.info("[TourController] createTour tourName : {}", tourCreateDto.getTourName());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String userId = principalDetails.getUserId();

        return ResponseEntity.ok(tourService.saveTour(userId, tourCreateDto));
    }
}
