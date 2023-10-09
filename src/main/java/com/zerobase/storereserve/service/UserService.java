package com.zerobase.storereserve.service;

import com.zerobase.storereserve.domain.User;
import com.zerobase.storereserve.dto.LoginTokenDto;
import com.zerobase.storereserve.dto.UserLoginDto;
import com.zerobase.storereserve.dto.UserRegisterDto;
import com.zerobase.storereserve.exception.impl.AlreadyExistsUserException;
import com.zerobase.storereserve.exception.impl.PasswordNotMatchException;
import com.zerobase.storereserve.exception.impl.UserNotFoundException;
import com.zerobase.storereserve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유저를 찾을 수 없습니다."));
    }

    public UserRegisterDto userRegister(UserRegisterDto userRegisterDto) {
        usernameDuplicatedCheck(userRegisterDto);

        userRegisterDto.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));

        User user = userRepository.save(User
                .builder()
                .username(userRegisterDto.getUsername())
                .password(userRegisterDto.getPassword())
                .phone(userRegisterDto.getPhone())
                .isPartner(userRegisterDto.isPartner())
                .roles(userRegisterDto.getRoles())
                .build());

        log.info(user.getUsername() + "님의 회원가입이 완료되었습니다.");

        return UserRegisterDto.fromEntity(user);
    }

    private void usernameDuplicatedCheck(UserRegisterDto userRegisterDto) {
        if (userRepository.existsByUsername(
                userRegisterDto.getUsername())) {
            throw new AlreadyExistsUserException();
        }
    }

    public LoginTokenDto authenticate(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException());

        if (!passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }

        return LoginTokenDto.builder()
                .username(user.getUsername())
                .roles(user.getRoles())
                .build();
    }
}
