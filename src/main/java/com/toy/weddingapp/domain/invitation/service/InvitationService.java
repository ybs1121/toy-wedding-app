package com.toy.weddingapp.domain.invitation.service;

import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationUpdateRequest;

public interface InvitationService {

    Long save(InvitationAddRequest request);

    InvitationResponse getOne(Long id);

    Void delete(Long id);

    Long update(InvitationUpdateRequest request);
}
