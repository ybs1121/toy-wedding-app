package com.toy.weddingapp.domain.invitation.dto;


import com.toy.weddingapp.common.constant.Status;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InvitationResponse {


    private Long id;

    private Weddings weddings;

    private String url;

    private String shortUrl;

    private String title;

    private String message;

    private String templateId;

    private Status status;

    private LocalDate startDate;

    private LocalDate endDate;
}
