package com.uhban.travelsync.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="plan_table")
public class Plan {
    @Id
    @Column(name = "planId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "tourId", referencedColumnName = "tourId")
    private Tour tour;

    private Integer day;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm", timezone="Asia/Seoul")
    private LocalTime time;

    private String planContent;
    private String planTitle;

    @Builder
    public Plan(Long planId, Tour tour, Integer day, LocalTime time, String planContent, String planTitle) {
        this.planId = planId;
        this.tour = tour;
        this.day = day;
        this.time = time;
        this.planContent = planContent;
        this.planTitle = planTitle;
    }
}
