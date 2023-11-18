package com.uhban.travelsync.data.dto.group;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GroupMemberDto {
    private Long groupId;
    private String userId;
    private String userName;

    @Builder
    public GroupMemberDto(Long groupId, String userId, String userName) {
        this.groupId = groupId;
        this.userId = userId;
        this.userName = userName;
    }

}
