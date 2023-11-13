package com.uhban.travelsync.data.entity;

import jakarta.persistence.*;

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

    private Date time;
    private Date day;
    private String content;
    private String planTitle;
}
