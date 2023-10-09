package com.zerobase.storereserve.dto;

import com.zerobase.storereserve.domain.User;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
public class UserRegisterDto {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @NotNull
    private boolean isPartner;

    private List<String> roles;

    public static UserRegisterDto fromEntity(User user) {
        return UserRegisterDto.builder()
                .username(user.getUsername())
                .phone(user.getPhone())
                .isPartner(user.isPartner())
                .roles(user.getRoles())
                .build();
    }
}
