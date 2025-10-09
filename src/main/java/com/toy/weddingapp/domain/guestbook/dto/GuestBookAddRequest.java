package com.toy.weddingapp.domain.guestbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GuestBookAddRequest {

    @NotBlank
    private String name;

    @NotNull
    private Long invitationId;

    @NotBlank
    private String message;

    @NotBlank
    private String password;

}
