package com.uhban.travelsync.service.impl;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.config.jwt.TokenProvider;
import com.uhban.travelsync.data.dto.user.TokenResponseDto;
import com.uhban.travelsync.data.entity.User;
import com.uhban.travelsync.data.repository.UserRepository;
import com.uhban.travelsync.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public UserServiceImpl(UserRepository userRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
    }


    public void saveUser(User user) {
        userRepository.save(user);
    }
    @Transactional
    public TokenResponseDto generateTokens (PrincipalDetails principalDetails)
    {
        String accessToken = tokenProvider.generateAccessToken(principalDetails);
        String refreshToken = tokenProvider.generateRefreshToken(principalDetails);
        /*
        // Redis에 저장 - 만료 시간 설정을 통해 자동 삭제 처리
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
}
