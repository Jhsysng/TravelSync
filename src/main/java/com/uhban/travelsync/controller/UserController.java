package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.user.*;
import com.uhban.travelsync.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class UserController {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;


    public UserController(AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }
    @GetMapping("/user/info/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable String userId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("[UserController] getUser userId : {}", userId);
        if (!userId.equals(principalDetails.getUserId())) {
            log.error("[UserController] getUser 인증된 사용자가 아닙니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(userService.getUser(userId), HttpStatus.OK);
    }

    @GetMapping("/user/check/{userId}")
    public ResponseEntity<String> checkUser(@PathVariable String userId) {
        log.info("[UserController] checkUser userId : {}", userId);
        if (userService.userExists(userId)) {
            return new ResponseEntity<>("already exists", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("not exists", HttpStatus.OK);
        }
    }

    @PostMapping("/user/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        //todo : [UserController] value validation , password validation
        log.info("userSignUpDTO : {}", userDto.getUserId());
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        if(userService.userExists(userDto.getUserId())) {
            log.info("SingUp Fail");
            return ResponseEntity.ok("SingUp Fail");
        }
        userService.saveUser(userDto);
        log.info("SingUp Success");
        return ResponseEntity.ok("SingUp Success");
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        log.info("[UserController]login userid : {}", loginDto.getUserId());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("[UserController]login Success : {}", loginDto.getUserId());
            return new ResponseEntity<>(userService.generateTokens((PrincipalDetails) authentication.getPrincipal()), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("잘못된 비밀번호 입니다.", HttpStatus.UNAUTHORIZED);
        } catch (InternalAuthenticationServiceException e) {
            return new ResponseEntity<>("잘못된 아이디 입니다.", HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/user/change")
    public ResponseEntity<UserResponseDto> changeUser(@RequestBody UserChangeDto userChangeDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        String userId = principalDetails.getUserId();
        if (!userId.equals(userChangeDto.getUserId())) {
            log.error("[UserController] changeUser 인증된 사용자가 아닙니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("[UserController] changeUser userId : {}", userChangeDto.getUserId());
        return ResponseEntity.ok(userService.changeUser(userChangeDto));
    }
}
