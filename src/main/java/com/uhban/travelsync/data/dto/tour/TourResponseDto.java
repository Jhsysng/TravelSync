package com.uhban.travelsync.data.dto.tour;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TourResponseDto {
    private Long tourId;
    private String tourName;
    private String tourCompany;
    @Builder
    public TourResponseDto(Long tourId, String tourName, String tourCompany) {
        this.tourId = tourId;
        this.tourName = tourName;
        this.tourCompany = tourCompany;
    }
}
