package com.uhban.travelsync.data.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="group_table")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "guide", referencedColumnName = "userId")
    private User guide;

    private String groupName;
    private String groupPassword;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String nation;
    private String tourCompany;

    private Boolean toggleLoc;

    @OneToOne
    @JoinColumn(name = "tourId", referencedColumnName = "tourId")
    private Tour tour;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group_User> groupUsers = new ArrayList<>();

    @Builder
    public Group(Long groupId, User guide, String groupName, Boolean toggleLoc, String groupPassword, Date startDate,
                 Date endDate, String nation, String tourCompany, Tour tour) {
        this.groupId = groupId;
        this.guide = guide;
        this.groupName = groupName;
        this.toggleLoc = (toggleLoc != null) ? toggleLoc : false;;
        this.groupPassword = groupPassword;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nation = nation;
        this.tourCompany = tourCompany;
        this.tour = tour;
    }
}
