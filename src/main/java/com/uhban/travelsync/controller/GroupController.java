package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.group.*;
import com.uhban.travelsync.service.GroupService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class GroupController {
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/group/info/{userId}")
    public ResponseEntity<List<GroupInfoDto>> getGroupByUserId(@PathVariable String userId) {
        log.info("[GroupController] getGroupByUserId userId : {}", userId);
        return ResponseEntity.ok(groupService.getGroupByUserId(userId));
    }

    @GetMapping("/group/detail/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupDetail(@PathVariable Long groupId) {
        log.info("[GroupController] getGroupDetail groupId : {}", groupId);
        //todo : [GroupController] getGroupDetail 인증된 사용자가 그룹에 속해있는지 확인
        try{
            GroupResponseDto groupResponseDto = groupService.getGroup(groupId);
            return ResponseEntity.ok(groupResponseDto);
        }catch (IllegalArgumentException e){
            log.error("[GroupController] getGroupDetail 그룹이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/group/members/{groupId}")
    public ResponseEntity<List<GroupMemberDto>> getMembers(@PathVariable Long groupId){
        log.info("[GroupController] getGroupDetail groupId : {}", groupId);
        //todo : [GroupController] getMembers 인증된 사용자가 그룹에 속해있는지 확인
        try{
            List<GroupMemberDto> groupMemberDtoList = groupService.getGroupMembers(groupId);
            log.info("[GroupController] getMembers Success : {}", groupId);
            return ResponseEntity.ok(groupMemberDtoList);
        }catch (EntityNotFoundException e){
            log.error("[GroupController] getGroupDetail 그룹이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/group/create")
    public ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupCreateDto groupCreateDto) {
        log.info("[GroupController] saveGroup groupName : {}", groupCreateDto.getGroupName());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails principalDetails) {
            userId = principalDetails.getUserId();
        }else{
            log.error("[GroupController] saveGroup 인증되지 않은 사용자입니다.");
            return ResponseEntity.badRequest().build();
        }

        if(groupCreateDto.getGuide().equals(userId)) {
            log.info("[GroupController] saveGroup group 생성 합니다.");
        } else {
            log.error("[GroupController] saveGroup 본인이 아닌 유저가 그룹을 생성합니다.error");
            return ResponseEntity.badRequest().build();
        }
        GroupCreateDto groupCreateDto1 = GroupCreateDto.builder()
                .guide(groupCreateDto.getGuide())
                .groupName(groupCreateDto.getGroupName())
                .startDate(groupCreateDto.getStartDate())
                .endDate(groupCreateDto.getEndDate())
                .nation(groupCreateDto.getNation())
                .tourCompany(groupCreateDto.getTourCompany())
                .groupPassword(groupCreateDto.getGroupPassword())
                .build();
        log.info("[GroupController] saveGroup groupCreateDto : {}", groupCreateDto1);
        return ResponseEntity.ok(groupService.saveGroup(groupCreateDto));
    }

    @PostMapping("/group/join")
    public ResponseEntity<GroupInfoDto> joinGroup(@RequestBody GroupJoinDto groupJoinDto) {
        log.info("[GroupController] joinGroup groupId : {}", groupJoinDto.getGroupId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = "";
        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails principalDetails) {
            userId = principalDetails.getUserId();
        } else {
            log.error("[GroupController] joinGroup 인증되지 않은 사용자입니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try {
            GroupInfoDto groupInfoDto = groupService.joinGroup(userId, groupJoinDto);
            log.info("[GroupController] joinGroup Success : {}", groupInfoDto.getGroupId());
            return ResponseEntity.ok(groupInfoDto);
        } catch (IllegalArgumentException e) {
            log.error("[GroupController] 그룹 혹은 유저, 비밀번호가 틀립니다.");
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/group/setting")
    public ResponseEntity<GroupResponseDto> setGroupTour(@RequestBody GroupDto groupDto){
        log.info("[GroupController] setGroupTour groupId : {}", groupDto.getGroupId());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        String userId = principalDetails.getUserId();

        if(userId.equals(groupService.getGroup(groupDto.getGroupId()).getGuide())){
            log.info("[GroupController] setGroupTour 그룹 여행지를 변경합니다.");
        }else{
            log.error("[GroupController] setGroupTour 본인이 아닌 유저가 그룹 여행지를 변경합니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        try{
            GroupResponseDto groupResponseDto = groupService.changeGroup(groupDto);
            log.info("[GroupController] setGroupTour Success : {}", groupDto.getGroupId());
            return ResponseEntity.ok(groupResponseDto);
        }catch (IllegalArgumentException e){
            log.error("[GroupController] setGroupTour 그룹이 존재하지 않습니다.");
            return ResponseEntity.badRequest().build();
        }
    }

}
