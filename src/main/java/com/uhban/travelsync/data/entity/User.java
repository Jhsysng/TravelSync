package com.uhban.travelsync.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user_table")
public class User {
    @Id
    private String userId;

    private String name;
    private String phone;
    private String password;

    private Double latitude;
    private Double longitude;

    @OneToMany(mappedBy = "user")
    private List<Group_User> groupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Tour> tours = new ArrayList<>();

    @Builder
    public User(String userId, String name, String phone, String password, Double latitude, Double longitude) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
