package com.toy.weddingapp.domain.invitation.dto;

import com.toy.weddingapp.common.constant.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvitationFindRequest {

    @NotNull
    private Long weddingsId;

    @NotNull
    private String title;

    private String message;

    private String templateId;

    private Status status;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
}
