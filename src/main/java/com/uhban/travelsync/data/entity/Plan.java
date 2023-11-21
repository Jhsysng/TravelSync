package com.uhban.travelsync.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalTime;
import java.util.Date;

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

    private String content;
    private String planTitle;

    @Builder
    public Plan(Long planId, Tour tour, Integer day, LocalTime time, String content, String planTitle) {
        this.planId = planId;
        this.tour = tour;
        this.day = day;
        this.time = time;
        this.content = content;
        this.planTitle = planTitle;
    }
}
