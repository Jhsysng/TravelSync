package com.uhban.travelsync.data.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserChangeDto {
    private String userId;
    private String name;
    private String phone;

    @Builder
    public UserChangeDto(String userId, String name, String phone) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
    }
}
