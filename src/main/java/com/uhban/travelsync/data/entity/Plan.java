package com.uhban.travelsync.data.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name="plan_table")
public class Plan {
    @Id
    @Column(name = "planId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @ManyToOne
    @JoinColumn(name = "tourId", referencedColumnName = "tourId")
    private Tour tour;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date date;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm", timezone="Asia/Seoul")
    private LocalTime time;

    private String content;
    private String planTitle;
}
