package com.uhban.travelsync.data.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
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
    private String toggleLoc;
    private String groupPassword;
    private Date startDate;
    private Date endDate;
    private String nation;
    private String tourCompany;

    @OneToOne
    @JoinColumn(name = "tourId", referencedColumnName = "tourId")
    private Tour tour;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Group_User> groupUsers = new ArrayList<>();

    @Builder
    public Group(Long groupId, User guide, String groupName, String toggleLoc, String groupPassword, Date startDate,
                 Date endDate, String nation, String tourCompany, Tour tour) {
        this.groupId = groupId;
        this.guide = guide;
        this.groupName = groupName;
        this.toggleLoc = toggleLoc;
        this.groupPassword = groupPassword;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nation = nation;
        this.tourCompany = tourCompany;
        this.tour = tour;
    }
}
