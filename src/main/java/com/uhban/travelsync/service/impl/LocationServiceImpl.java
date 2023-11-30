package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.data.dto.location.LocationResponseDto;
import com.uhban.travelsync.data.dto.location.LocationUpdateDto;
import com.uhban.travelsync.data.entity.Group;
import com.uhban.travelsync.data.repository.GroupRepository;
import com.uhban.travelsync.data.repository.GroupUserRepository;
import com.uhban.travelsync.data.repository.UserRepository;
import com.uhban.travelsync.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LocationServiceImpl implements LocationService {
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;
    private final GroupUserRepository groupUserRepository;

    public LocationServiceImpl(UserRepository userRepository, GroupRepository groupRepository, GroupUserRepository groupUserRepository) {
        this.userRepository = userRepository;
        this.groupRepository = groupRepository;
        this.groupUserRepository = groupUserRepository;
    }

    @Transactional
    public void saveLocation(String userId, LocationUpdateDto locationUpdateDto){
        log.info("[LocationService] saveLocation userId : {}", userId);
        userRepository.findByUserId(userId)
                .map(user -> {
                    user.setLatitude(locationUpdateDto.getLatitude());
                    user.setLongitude(locationUpdateDto.getLongitude());
                    return user;
                })
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }

    @Transactional
    public LocationResponseDto getLocation(String userId, Long groupId) {
        log.info("[LocationService] getLocation userId : {}", userId);
        //todo : [LocationService] getLocation 인증된 사용자가 그룹에 속해있는지 확인
        if (!groupUserRepository.existsByUser_UserIdAndGroup_GroupId(userId, groupId)) {
            log.error("[LocationService] getLocation 인증된 사용자가 그룹에 속해있지 않습니다.");
            throw new IllegalArgumentException("인증된 사용자가 그룹에 속해있지 않습니다.");
        }

        return userRepository.findByUserId(userId)
                .map(user -> new LocationResponseDto(
                        user.getUserId(),
                        user.getLatitude(),
                        user.getLongitude()
                ))
                .orElseThrow(() -> new IllegalArgumentException("사용자가 존재하지 않습니다."));
    }


    @Transactional
    public List<LocationResponseDto> getLocations(String userId, Long groupId){
        log.info("[LocationService] getLocations userId : {}, groupId : {}", userId, groupId);
        Optional<Group> groupOptional = groupRepository.findById(groupId);
        if(groupOptional.isPresent() && !groupOptional.get().getGuide().getUserId().equals(userId)){
            log.error("[LocationService] getLocations 인증된 사용자가 그룹의 가이드가 아닙니다.");
            throw new IllegalArgumentException("인증된 사용자가 그룹의 가이드가 아닙니다.");
        }

        return groupUserRepository.findAllByGroup_GroupId(groupId).stream()
                .map(group_user -> new LocationResponseDto(
                        group_user.getUser().getUserId(),
                        group_user.getUser().getLatitude(),
                        group_user.getUser().getLongitude()
                ))
                .collect(Collectors.toList());
    }
}
