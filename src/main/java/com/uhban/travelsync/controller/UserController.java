package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.config.jwt.TokenProvider;
import com.uhban.travelsync.data.dto.user.LoginDto;
import com.uhban.travelsync.data.dto.user.TokenResponseDto;
import com.uhban.travelsync.data.dto.user.UserSignUpDTO;
import com.uhban.travelsync.data.entity.User;
import com.uhban.travelsync.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    public UserController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/user/check/{userId}")
    public ResponseEntity<String> checkUser(@PathVariable String userId) {
        log.info("[checkUser] userId : {}", userId);
        if (userService.userExists(userId)) {
            return new ResponseEntity<String>("already exists", HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("not exists", HttpStatus.OK);
        }
    }

    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpDTO userSignUpDTO) {
        log.info("userSignUpDTO : {}", userSignUpDTO);
        userSignUpDTO.setPassword(bCryptPasswordEncoder.encode(userSignUpDTO.getPassword()));
        User user = User.builder()
                .userId(userSignUpDTO.getUserId())
                .name(userSignUpDTO.getUserName())
                .password(userSignUpDTO.getPassword())
                .phone(userSignUpDTO.getPhone())
                .build();
        userService.saveUser(user);
        log.info("SingUp Success");
        return ResponseEntity.ok("SingUp Success");
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        log.info("loginDto : {}", loginDto.getUserId());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<TokenResponseDto>(userService.generateTokens((PrincipalDetails) authentication.getPrincipal()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<String>("잘못된 비밀번호 입니다.", HttpStatus.UNAUTHORIZED);
        } catch (InternalAuthenticationServiceException e) {
            return new ResponseEntity<String>("잘못된 아이디 입니다.", HttpStatus.UNAUTHORIZED);
        }
    }
}
