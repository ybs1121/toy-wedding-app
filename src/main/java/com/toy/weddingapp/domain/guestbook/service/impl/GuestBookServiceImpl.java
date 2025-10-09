package com.toy.weddingapp.domain.guestbook.service.impl;

import com.toy.weddingapp.domain.guestbook.dto.GuestBookAddRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookModRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookResponse;
import com.toy.weddingapp.domain.guestbook.entity.GuestBook;
import com.toy.weddingapp.domain.guestbook.mapper.GuestBookMapper;
import com.toy.weddingapp.domain.guestbook.repository.GuestBookJpaRepository;
import com.toy.weddingapp.domain.guestbook.service.GuestBookService;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import com.toy.weddingapp.domain.invitation.mapper.InvitationMapper;
import com.toy.weddingapp.domain.invitation.repository.InvitationRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookJpaRepository guestBookJpaRepository;
    private final InvitationRepository invitationRepository;
    private final GuestBookMapper guestBookMapper;
    private final EntityManager em;

    @Override
    public Long save(GuestBookAddRequest addRequest) {

        // 검증
        Invitation invitation = invitationRepository.findById(addRequest.getInvitationId())
                .orElseThrow(() -> new RuntimeException("Invitation is not exist "));

        GuestBook saveGuestBook = guestBookJpaRepository.save(guestBookMapper.toEntity(addRequest, invitation));

        return saveGuestBook.getId();
    }

    @Override
    public GuestBookResponse findOne(Long id) {
        GuestBook guestBook = guestBookJpaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("GuestBook is not exist "));
        return guestBookMapper.toResponse(guestBook, guestBook.getInvitation().getId());
    }

    @Override
    public GuestBookResponse update(GuestBookModRequest modRequest) {
        GuestBook guestBook = guestBookJpaRepository.findById(modRequest.getId())
                .orElseThrow(() -> new RuntimeException("GuestBook is not exist "));

        if (!modRequest.getPassword().equals(guestBook.getPassword())) {
            throw new RuntimeException("Passwords don't match");
        }

        guestBook.setName(modRequest.getName());
        guestBook.setMessage(modRequest.getMessage());

        return guestBookMapper.toResponse(guestBook, guestBook.getInvitation().getId());
    }

    @Override
    public void delete(Long id) {
        guestBookJpaRepository.deleteById(id);
    }
}
