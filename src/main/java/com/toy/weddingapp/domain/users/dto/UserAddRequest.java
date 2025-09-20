package com.toy.weddingapp.domain.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserAddRequest {


    @NotBlank
    @Size(min = 6, max = 50)
    private String userId;

    @NotBlank
    @Size(min = 8, max = 50)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "비밀번호는 문자와 숫자를 모두 포함해야 합니다.")
    private String password;

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotBlank
    @Pattern(regexp = "USER|ADMIN", message = "역할은 USER 또는 ADMIN 이어야 합니다.")
    private String role;
}
