package com.uhban.travelsync.data.dto.tour;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TourCreateDto {
    private String tourName;
    private String tourCompany;
    @Builder
    public TourCreateDto(String tourName, String tourCompany) {
        this.tourName = tourName;
        this.tourCompany = tourCompany;
    }
}
