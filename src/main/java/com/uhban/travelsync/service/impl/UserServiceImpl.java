package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.config.jwt.TokenProvider;
import com.uhban.travelsync.data.dto.user.TokenResponseDto;
import com.uhban.travelsync.data.dto.user.UserChangeDto;
import com.uhban.travelsync.data.dto.user.UserDto;
import com.uhban.travelsync.data.dto.user.UserResponseDto;
import com.uhban.travelsync.data.entity.User;
import com.uhban.travelsync.data.repository.UserRepository;
import com.uhban.travelsync.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }
    @Transactional
    public UserResponseDto getUser(String userId) {
        log.info("[UserService] getUser : {}", userId);
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.error("해당 사용자 : {}를 찾을 수 없습니다. : ", userId);
                    throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. : " + userId);
                });
        log.info("[UserService] getUser Success : {}", userId);
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .phone(user.getPhone())
                .build();
    }
    @Transactional
    public void saveUser(UserDto userDto) {
        User user = User.builder()
                .userId(userDto.getUserId())
                .password(userDto.getPassword())
                .name(userDto.getName())
                .build();
        userRepository.save(user);
    }

    @Transactional
    public UserResponseDto changeUser(UserChangeDto userChangeDto) {
        log.info("[UserService] changeUser : {}", userChangeDto.getUserId());
        User user = userRepository.findByUserId(userChangeDto.getUserId())
                .orElseThrow(() -> {
                    log.error("해당 사용자 : {}를 찾을 수 없습니다. : ", userChangeDto.getUserId());
                    throw new IllegalArgumentException("해당 사용자를 찾을 수 없습니다. : " + userChangeDto.getUserId());
                });

        User changeUser = User.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .name(userChangeDto.getName())
                .build();
        userRepository.save(changeUser);
        log.info("[UserService] changeUser Success : {}", userChangeDto.getUserId());
        return UserResponseDto.builder()
                .userId(changeUser.getUserId())
                .name(changeUser.getName())
                .phone(changeUser.getPhone())
                .build();
    }

    public TokenResponseDto generateTokens (PrincipalDetails principalDetails)
    {
        String accessToken = tokenProvider.generateAccessToken(principalDetails);
        String refreshToken = tokenProvider.generateRefreshToken(principalDetails);
        /*
        // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 redis 기능 활성화시 @Transactional 추가
        redisTemplate.opsForValue().set(
                principalDetails.getUsername(),
                refreshToken,
                refreshTokenExpirationMinutes,
                TimeUnit.MINUTES
        );*/

        return TokenResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Transactional
    public boolean userExists(String userId) {
        return userRepository.existsByUserId(userId);
    }
}
