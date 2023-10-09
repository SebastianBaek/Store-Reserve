package com.zerobase.storereserve.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginTokenDto {

    private String username;

    private List<String> roles;
}
