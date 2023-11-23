package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.tour.TourCreateDto;
import com.uhban.travelsync.data.dto.tour.TourDto;
import com.uhban.travelsync.data.dto.tour.TourResponseDto;
import com.uhban.travelsync.data.repository.GroupUserRepository;
import com.uhban.travelsync.service.GroupService;
import com.uhban.travelsync.service.TourService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<TourResponseDto> getGroupTour(@PathVariable Long groupId
        , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[TourController] getGroupTour groupId : {}", groupId);
        //todo : [TourController] getGroupTour 인증된 사용자가 그룹에 속해있는지 확인
        if(!groupService.isUserInGroup(principalDetails.getUserId(), groupId)){
            log.error("[TourController] getGroupTour 인증된 사용자가 그룹에 속해있지 않습니다.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

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
    public ResponseEntity<List<TourResponseDto>> getTourList(@PathVariable String userId,
                                                             @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[TourController] getTourList userId : {}", userId);
        String authId = principalDetails.getUserId();
        if(!authId.equals(userId)){
            log.error("[TourController] getTourList 인증된 사용자가 아닙니다.");
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(tourService.getTourList(userId));
    }
    @PostMapping("/tour")
    public ResponseEntity<TourResponseDto> createTour(@RequestBody TourCreateDto tourCreateDto,
                                                      @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[TourController] createTour tourName : {}", tourCreateDto.getTourName());
        //인증정보 가져온 뒤 userId와 함께 saveTour logic 처리
        String userId = principalDetails.getUserId();
        return ResponseEntity.ok(tourService.saveTour(userId, tourCreateDto));
    }

    @PutMapping("/tour")
    public ResponseEntity<TourResponseDto> changeTour(@RequestBody TourDto tourDto,
                                                      @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("[TourController] changeTour tourId : {}", tourDto.getTourId());
        String userId = principalDetails.getUserId();

        return ResponseEntity.ok(tourService.changeTour(userId, tourDto));
    }

    @DeleteMapping("/tour/{tourId}")
    public ResponseEntity<String> deleteTour(@PathVariable Long tourId,
                                             @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("[TourController] deleteTour tourId : {}", tourId);
        String userId = principalDetails.getUserId();

        tourService.deleteTour(userId, tourId);
        return ResponseEntity.ok("delete success");
    }

}
