package com.uhban.travelsync.controller;

import com.uhban.travelsync.data.dto.group.GroupCreateDto;
import com.uhban.travelsync.data.dto.group.GroupInfoDto;
import com.uhban.travelsync.data.dto.group.GroupResponseDto;
import com.uhban.travelsync.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/group/{userId}")
    public ResponseEntity<List<GroupInfoDto>> getGroupByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(groupService.getGroupByUserId(userId));
    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupDetail(@PathVariable Long groupId) {
        return ResponseEntity.ok(groupService.getGroup(groupId));
    }

    @PostMapping("/group/create")
    public ResponseEntity<GroupResponseDto> saveGroup(GroupCreateDto groupCreateDto) {
        return ResponseEntity.ok(groupService.saveGroup(groupCreateDto));
    }

}
