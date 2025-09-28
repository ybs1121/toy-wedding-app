package com.toy.weddingapp.domain.invitation.mapper;

import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InvitationMapper {

    Invitation toEntity(InvitationAddRequest request, Weddings weddings);

    InvitationResponse toDto(Invitation invitation);
}
