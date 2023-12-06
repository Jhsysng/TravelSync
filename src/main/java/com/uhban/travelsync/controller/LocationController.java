package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.location.LocationMemberResponseDto;
import com.uhban.travelsync.data.dto.location.LocationResponseDto;
import com.uhban.travelsync.data.dto.location.LocationUpdateDto;
import com.uhban.travelsync.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping("/location/guide/{groupId}")
    public ResponseEntity<LocationResponseDto> getLocation(@PathVariable Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String userId = principalDetails.getUserId();
        log.info("[LocationController] getLocation groupId : {}", groupId);
        return ResponseEntity.ok(locationService.getLocation(userId, groupId));
    }

    @GetMapping("/location/member/{groupId}")
    public ResponseEntity<List<LocationResponseDto>> getLocations(@PathVariable Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //해당 그룹이 off일 경우 에러 반환
        String userId = principalDetails.getUserId();
        log.info("[LocationController] getLocations groupId : {}", groupId);
        return ResponseEntity.ok(locationService.getLocations(userId, groupId));
    }

    @GetMapping("/location/countmember/{groupId}")
    public ResponseEntity<Integer> checkMember(@PathVariable Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String userId = principalDetails.getUserId();
        log.info("[LocationController] checkMember groupId : {}", groupId);
        return ResponseEntity.ok(locationService.countMembers(userId, groupId));
    }

    @GetMapping("/location/checkmember/{groupId}")
    public ResponseEntity<List<LocationMemberResponseDto>> getMembersCheck(@PathVariable Long groupId, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String userId = principalDetails.getUserId();
        log.info("[LocationController] getMembersCheck groupId : {}", groupId);
        return ResponseEntity.ok(locationService.getMembersCheck(userId, groupId));
    }


    @PutMapping("/location")
    public ResponseEntity<String> updateLocation(@RequestBody LocationUpdateDto locationUpdateDto, @AuthenticationPrincipal PrincipalDetails principalDetails){
        String userId = principalDetails.getUserId();
        log.info("[LocationController] updateLocation latitude : {}", userId);
        locationService.saveLocation(userId, locationUpdateDto);
        return ResponseEntity.ok().build();
    }
}
