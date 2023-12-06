package com.uhban.travelsync.data.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationMemberResponseDto {
    private String userId;
    private String userName;
    private Double latitude;
    private Double longitude;
    private Boolean isNear;

    @Builder
    public LocationMemberResponseDto(String userId, String userName, Double latitude, Double longitude, Boolean isNear) {
        this.userId = userId;
        this.userName = userName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.isNear = isNear;
    }
}
