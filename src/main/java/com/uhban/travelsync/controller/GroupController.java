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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<List<GroupInfoDto>> getGroupByUserId(@PathVariable final String userId
            , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[GroupController] getGroupByUserId userId : {}", userId);
        if (!userId.equals(principalDetails.getUserId())) {
            log.error("[GroupController] getGroupByUserId 인증된 사용자가 아닙니다.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return ResponseEntity.ok(groupService.getGroupByUserId(userId));
    }

    @GetMapping("/group/detail/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupDetail(@PathVariable Long groupId
            , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[GroupController] getGroupDetail groupId : {}", groupId);
        if(!groupService.isUserInGroup(principalDetails.getUserId(), groupId)){
            log.error("[GroupController] getGroupDetail 인증된 사용자가 그룹에 속해있지 않습니다.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try{
            GroupResponseDto groupResponseDto = groupService.getGroup(groupId);
            return ResponseEntity.ok(groupResponseDto);
        }catch (IllegalArgumentException e){
            log.error("[GroupController] getGroupDetail 그룹이 존재하지 않습니다.");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/group/members/{groupId}")
    public ResponseEntity<List<GroupMemberDto>> getMembers(@PathVariable Long groupId
            , @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("[GroupController] getGroupDetail groupId : {}", groupId);
        if(!groupService.isUserInGroup(principalDetails.getUserId(), groupId)){
            log.error("[GroupController] getGroupDetail 인증된 사용자가 그룹에 속해있지 않습니다.");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
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
    public ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupCreateDto groupCreateDto
            , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[GroupController] saveGroup groupName : {}", groupCreateDto.getGroupName());
        if(!groupCreateDto.getGuide().equals(principalDetails.getUserId())){
            log.error("[GroupController] saveGroup 인증된 사용자가 아닙니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("[GroupController] saveGroup group 생성 시작 {}", groupCreateDto.getGroupName());
        return ResponseEntity.ok(groupService.saveGroup(groupCreateDto));
    }

    @PostMapping("/group/join")
    public ResponseEntity<GroupInfoDto> joinGroup(@RequestBody GroupJoinDto groupJoinDto
            , @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[GroupController] joinGroup groupId : {}", groupJoinDto.getGroupId());
        String userId = principalDetails.getUserId();
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
    public ResponseEntity<GroupResponseDto> setGroupTour(@RequestBody GroupDto groupDto
            , @AuthenticationPrincipal PrincipalDetails principalDetails){
        log.info("[GroupController] setGroupTour groupId : {}", groupDto.getGroupId());
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
