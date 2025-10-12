package com.toy.weddingapp.domain.invitation.dto;


import lombok.Data;

@Data
public class InvitationStatusStatisticsResponse {
    private Long waitingCount;
    private Long progressCount;
    private Long closedCount;
}
