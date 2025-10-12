package com.toy.weddingapp.domain.invitation.service;

import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationStatusStatisticsResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationUpdateRequest;

import java.util.List;

public interface InvitationService {

    Long save(InvitationAddRequest request);

    InvitationResponse getOne(Long id);

    Void delete(Long id);

    Long update(InvitationUpdateRequest request);

    InvitationResponse createUrl(Long id);


    InvitationStatusStatisticsResponse getInvitationStatusStatistics();

    List<InvitationResponse> searchInvitations();

}
