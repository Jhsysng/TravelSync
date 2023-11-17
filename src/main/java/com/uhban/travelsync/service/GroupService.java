package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.group.GroupCreateDto;
import com.uhban.travelsync.data.dto.group.GroupDto;
import com.uhban.travelsync.data.dto.group.GroupInfoDto;
import com.uhban.travelsync.data.dto.group.GroupResponseDto;

import java.util.List;

public interface GroupService {
    List<GroupInfoDto> getGroupByUserId(String userId);
    GroupResponseDto getGroup(Long groupId);
    GroupResponseDto saveGroup(GroupCreateDto groupCreateDto);

}
