package com.uhban.travelsync.data.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GroupUserId implements Serializable {
    private String user;
    private Long group;
}
