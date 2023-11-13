package com.uhban.travelsync.data.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(GroupUserId.class)
@Table(name="group_user_table")
public class Group_User {
    @Id
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "groupId")
    private Group group;
}
