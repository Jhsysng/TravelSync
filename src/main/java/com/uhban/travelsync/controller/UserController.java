package com.uhban.travelsync.controller;

import com.uhban.travelsync.config.auth.PrincipalDetails;
import com.uhban.travelsync.data.dto.user.LoginDto;
import com.uhban.travelsync.data.dto.user.UserChangeDto;
import com.uhban.travelsync.data.dto.user.UserDto;
import com.uhban.travelsync.data.dto.user.UserResponseDto;
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

    //Dependency Injection
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    //정규식
    private static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&]).{10,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String PHONE_REGEX = "^\\d{11}$";
    private static final String NAME_REGEX = "^[가-힣]{2,4}$";


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
        log.info("[UserController] signup userid : {}", userDto.getUserId());
        //비밀번호 10자이상 특수문자 포함 검증 및 이메일 형식 아이디 검증
        if (!userDto.getPassword().matches(PASSWORD_REGEX)) {
            log.error("[UserController] signup : 비밀번호가 조건에 맞지 않습니다.");
            return ResponseEntity.badRequest().body("Not Password Format");
        }else if(!userDto.getUserId().matches(EMAIL_REGEX)){
            log.error("[UserController] signup : 이메일 형식이 아닙니다.");
            return ResponseEntity.badRequest().body("Not Email Format");
        }else if(!userDto.getPhone().matches(PHONE_REGEX)){
            log.error("[UserController] signup : 전화번호 형식이 아닙니다.");
            return ResponseEntity.badRequest().body("Not Phone Format 01012345678");
        }else if (!userDto.getName().matches(NAME_REGEX)){
            log.error("[UserController] signup : 이름 형식이 아닙니다.");
            return ResponseEntity.badRequest().body("Not Name Format");
        }
        userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        if(userService.userExists(userDto.getUserId())) {
            log.error("[UserController] signup SignUp Fail");
            return ResponseEntity.ok("SignUp Fail");
        }
        userService.saveUser(userDto);
        log.info("[UserController] signup : SignUp Success");
        return ResponseEntity.ok("SignUp Success");
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        log.info("[UserController]login userid : {}", loginDto.getUserId());
        if (!loginDto.getPassword().matches(PASSWORD_REGEX)) {
            log.error("[UserController] login : 비밀번호가 조건에 맞지 않습니다. {}", loginDto.getPassword());
            return ResponseEntity.badRequest().body("Not Password Format");
        }else if(!loginDto.getUserId().matches(EMAIL_REGEX)){
            log.error("[UserController] login : 이메일 형식이 아닙니다.");
            return ResponseEntity.badRequest().body("Not Email Format");
        }
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
        //비밀번호가 null 이거나 ""경우가 아니면 정규식 체크
        if (!userId.equals(userChangeDto.getUserId())) {
            log.error("[UserController] changeUser 인증된 사용자가 아닙니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }else if(!userChangeDto.getPhone().matches(PHONE_REGEX)) {
            log.error("[UserController] changeUser : 전화번호 형식이 아닙니다.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else if (!userChangeDto.getName().matches(NAME_REGEX)){
            log.error("[UserController] changeUser : 이름 형식이 아닙니다.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else if (userChangeDto.getPassword() != null && !userChangeDto.getPassword().matches(PASSWORD_REGEX)) {
            log.error("[UserController] changeUser : 비밀번호가 조건에 맞지 않습니다.");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        log.info("[UserController] changeUser userId : {}", userChangeDto.getUserId());
        //
        return ResponseEntity.ok(userService.changeUser(userChangeDto));
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (!userId.equals(principalDetails.getUserId())) {
            log.error("[UserController] deleteUser 인증된 사용자가 아닙니다.");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("[UserController] deleteUser userId : {}", userId);
        userService.deleteUser(userId);
        return ResponseEntity.ok("delete success");
    }
}
