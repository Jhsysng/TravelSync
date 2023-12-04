package com.uhban.travelsync.service;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.user.TokenResponseDto;
import com.uhban.travelsync.data.dto.user.UserChangeDto;
import com.uhban.travelsync.data.dto.user.UserDto;
import com.uhban.travelsync.data.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto getUser(String userId);
    void saveUser(UserDto userDto);
    UserResponseDto changeUser(UserChangeDto userChangeDto);
    TokenResponseDto generateTokens (PrincipalDetails principalDetails);
    void deleteUser(String userId);
    boolean userExists(String userId);
}
