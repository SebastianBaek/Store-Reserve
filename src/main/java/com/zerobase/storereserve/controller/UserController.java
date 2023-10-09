package com.zerobase.storereserve.controller;

import com.zerobase.storereserve.dto.LoginTokenDto;
import com.zerobase.storereserve.dto.UserLoginDto;
import com.zerobase.storereserve.dto.UserRegisterDto;
import com.zerobase.storereserve.security.TokenProvider;
import com.zerobase.storereserve.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

    // 회원가입 api
    @PostMapping("/register")
    public ResponseEntity<UserRegisterDto> userRegister(
            @RequestBody @Valid UserRegisterDto userRegisterDto) {
        return ResponseEntity.ok(userService.userRegister(userRegisterDto));
    }

    // 로그인 및 토큰 발행 api
    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid UserLoginDto userLoginDto) {
        LoginTokenDto loginTokenDto = userService.authenticate(userLoginDto);
        String token = tokenProvider.generateToken(loginTokenDto.getUsername(),
                loginTokenDto.getRoles());

        return ResponseEntity.ok(token);
    }
}
