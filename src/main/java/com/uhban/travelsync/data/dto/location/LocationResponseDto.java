package com.uhban.travelsync.data.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationResponseDto {
    private String userId;
    private String userName;
    private Double latitude;
    private Double longitude;

    @Builder
    public LocationResponseDto(String userId, String userName, Double latitude, Double longitude) {
        this.userId = userId;
        this.userName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
