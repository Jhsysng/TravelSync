package com.uhban.travelsync.data.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSignUpDTO {
    private String userId;
    private String userName;
    private String password;
    private String phone;
}
