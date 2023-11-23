package com.uhban.travelsync.data.dto.plan;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@NoArgsConstructor
public class PlanResponseDto {
    private Long planId;
    private Long tourId;
    private Integer day;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm", timezone="Asia/Seoul")
    private LocalTime time;
    private String planTitle;
    private String planContent;

    @Builder
    public PlanResponseDto(Long planId, Long tourId, Integer day, LocalTime time, String planTitle, String planContent) {
        this.planId = planId;
        this.tourId = tourId;
        this.day = day;
        this.time = time;
        this.planTitle = planTitle;
        this.planContent = planContent;
    }
}
