package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.data.dto.group.GroupCreateDto;
import com.uhban.travelsync.data.dto.group.GroupInfoDto;
import com.uhban.travelsync.data.dto.group.GroupResponseDto;
import com.uhban.travelsync.data.entity.Group;
import com.uhban.travelsync.data.repository.GroupRepository;
import com.uhban.travelsync.data.repository.GroupUserRepository;
import com.uhban.travelsync.data.repository.UserRepository;
import com.uhban.travelsync.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;
    private final GroupUserRepository groupUserRepository;

    public GroupServiceImpl(GroupRepository groupRepository, UserRepository userRepository, GroupUserRepository groupUserRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.groupUserRepository = groupUserRepository;
    }
    @Transactional
    public List<GroupInfoDto> getGroupByUserId(String userId) {
        Optional<List<Group>> optionalGroups = groupRepository.findByUserId(userId);

        if(optionalGroups.isPresent()) {
            List<Group> groups = optionalGroups.get();

            return groups.stream()
                    .map(group -> GroupInfoDto.builder()
                            .groupId(group.getGroupId())
                            .guide(group.getGuide().getName()) // 가이드의 이름
                            .groupName(group.getGroupName())
                            .startDate(group.getStartDate())
                            .endDate(group.getEndDate())
                            .tourCompany(group.getTourCompany())
                            .build())
                    .collect(Collectors.toList());
        } else {
            //없을 경우 빈 리스트 반환
            return Collections.emptyList();
        }
    }
    @Transactional
    public GroupResponseDto getGroup(Long groupId) {
        Group group = groupRepository.findByGroupId(groupId)
                .orElseThrow(() -> {
                    log.error("해당 그룹 : {}를 찾을 수 없습니다. : ", groupId);
                    throw new IllegalArgumentException("해당 그룹을 찾을 수 없습니다. : " + groupId);
                });

        return GroupResponseDto.builder()
                .groupId(group.getGroupId())
                .guide(group.getGuide().getUserId())
                .groupName(group.getGroupName())
                .startDate(group.getStartDate())
                .endDate(group.getEndDate())
                .nation(group.getNation())
                .tourCompany(group.getTourCompany())
                .toggleLoc(group.getToggleLoc())
                .build();
    }
    @Transactional
    public GroupResponseDto saveGroup(GroupCreateDto groupCreateDto) {
        Group group = Group.builder()
                .guide(userRepository.findByUserId(groupCreateDto.getGuide())
                        .orElseThrow(() -> {
                            log.error("해당 사용자 : {}를 찾을 수 없습니다. : ", groupCreateDto.getGuide());
                            throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. : " + groupCreateDto.getGuide());
                        }))
                .groupName(groupCreateDto.getGroupName())
                .startDate(groupCreateDto.getStartDate())
                .endDate(groupCreateDto.getEndDate())
                .nation(groupCreateDto.getNation())
                .tourCompany(groupCreateDto.getTourCompany())
                .build();

        groupRepository.save(group);

        return GroupResponseDto.builder()
                .groupId(group.getGroupId())
                .guide(group.getGuide().getUserId())
                .groupName(group.getGroupName())
                .startDate(group.getStartDate())
                .endDate(group.getEndDate())
                .nation(group.getNation())
                .tourCompany(group.getTourCompany())
                .toggleLoc(group.getToggleLoc())
                .build();
    }
}
