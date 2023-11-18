package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.group.GroupCreateDto;
import com.uhban.travelsync.data.dto.group.GroupInfoDto;
import com.uhban.travelsync.data.dto.group.GroupResponseDto;
import com.uhban.travelsync.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class GroupController {
    private final GroupService groupService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public GroupController(GroupService groupService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.groupService = groupService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/group/info/{userId}")
    public ResponseEntity<List<GroupInfoDto>> getGroupByUserId(@PathVariable String userId) {
        log.info("[GroupController] getGroupByUserId userId : {}", userId);
        return ResponseEntity.ok(groupService.getGroupByUserId(userId));
    }

    @GetMapping("/group/detail/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupDetail(@PathVariable Long groupId) {
        log.info("[GroupController] getGroupDetail groupId : {}", groupId);
        try{
            GroupResponseDto groupResponseDto = groupService.getGroup(groupId);
            return ResponseEntity.ok(groupResponseDto);
        }catch (IllegalArgumentException e){
            log.error("[GroupController] getGroupDetail 그룹이 존재하지 않습니다.");
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/group/create")
    public ResponseEntity<GroupResponseDto> saveGroup(@RequestBody GroupCreateDto groupCreateDto) {
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
                .groupPassword(bCryptPasswordEncoder.encode(groupCreateDto.getGroupPassword()))
                .build();
        log.info("[GroupController] saveGroup groupCreateDto : {}", groupCreateDto1);
        return ResponseEntity.ok(groupService.saveGroup(groupCreateDto));
    }

}
