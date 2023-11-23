package com.uhban.travelsync.data.dto.plan;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class PlanCreateDto {
    private Long tourId;
    private Integer day;
    private LocalTime time;
    private String planTitle;
    private String planContent;

    @Builder
    public PlanCreateDto(Long tourId, Integer day, LocalTime time, String planTitle, String planContent) {
        this.tourId = tourId;
        this.day = day;
        this.time = time;
        this.planTitle = planTitle;
        this.planContent = planContent;
    }
}
