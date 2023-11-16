package com.uhban.travelsync.service;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.user.TokenResponseDto;
import com.uhban.travelsync.data.entity.User;

public interface UserService {

    void saveUser(User user);
    TokenResponseDto generateTokens (PrincipalDetails principalDetails);
    boolean userExists(String userId);

}
