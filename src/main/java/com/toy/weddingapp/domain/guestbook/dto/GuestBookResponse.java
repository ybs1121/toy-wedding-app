package com.toy.weddingapp.domain.guestbook.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GuestBookResponse {

    private Long id;

    private String name;

    private Long invitationId;

    private String message;


}
