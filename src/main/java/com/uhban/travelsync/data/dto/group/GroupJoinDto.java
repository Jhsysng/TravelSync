package com.uhban.travelsync.data.dto.group;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupJoinDto {
    private Long groupId;
    private String groupPassword;

    public GroupJoinDto(Long groupId, String groupPassword) {
        this.groupId = groupId;
        this.groupPassword = groupPassword;
    }

}
