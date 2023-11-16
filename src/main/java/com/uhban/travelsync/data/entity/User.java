package com.uhban.travelsync.data.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="user_table")
public class User {
    @Id
    private String userId;

    private String name;
    private String phone;
    private String password;

    private String latitude;
    private String longitude;

    @OneToMany(mappedBy = "user")
    private List<Group_User> groupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Tour> tours = new ArrayList<>();
}
