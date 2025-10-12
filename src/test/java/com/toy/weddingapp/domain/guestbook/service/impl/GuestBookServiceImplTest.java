package com.toy.weddingapp.domain.guestbook.service.impl;

import com.toy.weddingapp.domain.guestbook.dto.GuestBookAddRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookModRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookResponse;
import com.toy.weddingapp.domain.guestbook.entity.GuestBook;
import com.toy.weddingapp.domain.guestbook.mapper.GuestBookMapper;
import com.toy.weddingapp.domain.guestbook.repository.GuestBookJpaRepository;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import com.toy.weddingapp.domain.invitation.repository.InvitationJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class GuestBookServiceImplTest {

    @InjectMocks
    private GuestBookServiceImpl guestBookServiceImpl;

    @Mock
    private GuestBookJpaRepository guestBookJpaRepository;

    @Mock
    private InvitationJpaRepository invitationJpaRepository;

    @Mock
    private EntityManager em;

    @Mock
    private GuestBookMapper guestBookMapper;

    @Test
    void 저장성공() {
        // G
        GuestBookAddRequest guestBookAddRequest = new GuestBookAddRequest();
        guestBookAddRequest.setName("tester");
        guestBookAddRequest.setMessage("Wow!!");
        guestBookAddRequest.setPassword("1234");
        guestBookAddRequest.setInvitationId(1L);

        Invitation invitation = new Invitation();
        invitation.setId(1L);

        GuestBook guestBook = new GuestBook();
        guestBook.setId(1L);
        guestBook.setMessage("Wow!!");
        guestBook.setPassword("1234");
        guestBook.setName("tester");
        guestBook.setInvitation(invitation);

        given(invitationJpaRepository.findById(1L)).willReturn(Optional.of(invitation));
        given(guestBookMapper.toEntity(guestBookAddRequest, invitation)).willReturn(guestBook);
        given(guestBookJpaRepository.save(guestBook)).willReturn(guestBook);

        // W
        Long save = guestBookServiceImpl.save(guestBookAddRequest);

        // T
        assertNotNull(save);
        assertEquals(guestBook.getId(), save);

    }

    @Test
    void 저장실패() {
        // G
        GuestBookAddRequest guestBookAddRequest = new GuestBookAddRequest();
        guestBookAddRequest.setName("tester");
        guestBookAddRequest.setMessage("Wow!!");
        guestBookAddRequest.setPassword("1234");
        guestBookAddRequest.setInvitationId(1L);

        Invitation invitation = new Invitation();
        invitation.setId(1L);

        GuestBook guestBook = new GuestBook();
        guestBook.setId(1L);
        guestBook.setMessage("Wow!!");
        guestBook.setPassword("1234");
        guestBook.setName("tester");
        guestBook.setInvitation(invitation);

        given(invitationJpaRepository.findById(1L)).willReturn(Optional.empty());
//        given(guestBookMapper.toEntity(guestBookAddRequest, invitation)).willReturn(guestBook);
//        given(guestBookJpaRepository.save(guestBook)).willReturn(guestBook);

        // W & T
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> guestBookServiceImpl.save(guestBookAddRequest));

        assertTrue(runtimeException.getMessage().contains("Invitation is not exist"));
    }

    @Test
    void 수정시_패스워드_정상입력() {

        // G
        GuestBookModRequest guestBookModRequest = new GuestBookModRequest();
        guestBookModRequest.setName("tester");
        guestBookModRequest.setMessage("Wow!!!!!!!");
        guestBookModRequest.setPassword("1234");
        guestBookModRequest.setId(1L);


        Invitation invitation = new Invitation();
        invitation.setId(1L);

        GuestBook guestBook = new GuestBook();
        guestBook.setId(1L);
        guestBook.setMessage("Wow!!!!!!!");
        guestBook.setPassword("1234");
        guestBook.setName("tester");
        guestBook.setInvitation(invitation);

        GuestBookResponse guestBookResponse = new GuestBookResponse();
        guestBookResponse.setId(1L);
        guestBookResponse.setMessage("Wow!!!!!!!");
        guestBookResponse.setName("tester");

//        given(invitationRepository.findById(1L)).willReturn(Optional.of(invitation));
        given(guestBookJpaRepository.findById(1L)).willReturn(Optional.of(guestBook));
        given(guestBookMapper.toResponse(guestBook, 1L)).willReturn(guestBookResponse);

        // W
        GuestBookResponse update = guestBookServiceImpl.update(guestBookModRequest);

        // T
        assertNotNull(update);
        assertEquals(guestBook.getId(), update.getId());
        assertEquals(guestBook.getName(), update.getName());
        assertEquals(guestBook.getMessage(), update.getMessage());
    }

    @Test
    void 수정시_패스워드_잘못입력() {
        GuestBookModRequest guestBookModRequest = new GuestBookModRequest();
        guestBookModRequest.setName("tester");
        guestBookModRequest.setMessage("Wow!!");
        guestBookModRequest.setPassword("1234");
        guestBookModRequest.setId(1L);


        Invitation invitation = new Invitation();
        invitation.setId(1L);

        GuestBook guestBook = new GuestBook();
        guestBook.setId(1L);
        guestBook.setMessage("Wow!!!!!!!");
        guestBook.setPassword("12345");
        guestBook.setName("tester");
        guestBook.setInvitation(invitation);

//        given(invitationRepository.findById(1L)).willReturn(Optional.of(invitation));
        given(guestBookJpaRepository.findById(1L)).willReturn(Optional.of(guestBook));


        // W & T
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> guestBookServiceImpl.update(guestBookModRequest));

        assertTrue(runtimeException.getMessage().contains("Passwords don't match"));
    }

}