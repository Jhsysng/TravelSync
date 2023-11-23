package com.uhban.travelsync.data.dto.location;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LocationUpdateDto {
    private Double latitude;
    private Double longitude;

    @Builder
    public LocationUpdateDto( Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
