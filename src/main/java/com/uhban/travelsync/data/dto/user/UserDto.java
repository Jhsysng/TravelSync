package com.uhban.travelsync.data.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String name;
    private String phone;
    private String password;

    @Builder
    public UserDto(String userId, String name, String phone, String password) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.password = password;
    }
}
