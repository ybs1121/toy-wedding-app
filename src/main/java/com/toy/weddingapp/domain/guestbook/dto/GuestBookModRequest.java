package com.toy.weddingapp.domain.guestbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GuestBookModRequest {

    @NotNull
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String message;

    @NotBlank
    private String password;

}
