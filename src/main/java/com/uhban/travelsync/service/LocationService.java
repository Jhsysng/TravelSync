package com.uhban.travelsync.service;

import com.uhban.travelsync.data.dto.location.LocationResponseDto;
import com.uhban.travelsync.data.dto.location.LocationUpdateDto;

import java.util.List;

public interface LocationService {
    void saveLocation(String userId, LocationUpdateDto locationUpdateDto);
    LocationResponseDto getLocation(String userId, Long groupId);
    List<LocationResponseDto> getLocations(String userId, Long groupId);
    Integer countMembers(String userId, Long groupId);
}
