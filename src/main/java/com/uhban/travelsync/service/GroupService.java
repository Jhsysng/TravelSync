package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.group.*;

import java.util.List;

public interface GroupService {
    List<GroupInfoDto> getGroupByUserId(String userId);
    List<GroupMemberDto> getGroupMembers(Long groupId);
    GroupResponseDto getGroup(Long groupId);
    GroupResponseDto saveGroup(GroupCreateDto groupCreateDto);
    GroupResponseDto changeGroup(GroupDto groupDto);
    GroupInfoDto joinGroup(String userId, GroupJoinDto groupJoinDto);


}
