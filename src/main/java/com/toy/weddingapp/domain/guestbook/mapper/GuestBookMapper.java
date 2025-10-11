package com.toy.weddingapp.domain.guestbook.mapper;

import com.toy.weddingapp.domain.guestbook.dto.GuestBookAddRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookResponse;
import com.toy.weddingapp.domain.guestbook.entity.GuestBook;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestBookMapper {
//    @Mapping(target = "message", source = "addRequest.message")
    GuestBook toEntity(GuestBookAddRequest addRequest, Invitation invitation);

//    @Mapping(source = "invitationId", target = "invitationId")
    GuestBookResponse toResponse(GuestBook guestBook, Long invitationId);
}
