package com.toy.weddingapp.domain.invitation.service.impl;

import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationUpdateRequest;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import com.toy.weddingapp.domain.invitation.mapper.InvitationMapper;
import com.toy.weddingapp.domain.invitation.repository.InvitationRepository;
import com.toy.weddingapp.domain.invitation.service.InvitationService;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import com.toy.weddingapp.domain.weddings.repository.WeddingRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl implements InvitationService {

    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final WeddingRepository weddingRepository;
    private final EntityManager em;

    @Override
    public Long save(InvitationAddRequest request) {
        Weddings weddingsRef = em.getReference(Weddings.class, request.getWeddingsId());
        Invitation saveInvitation = invitationRepository.save(invitationMapper.toEntity(request, weddingsRef));
        return saveInvitation.getId();
    }

    @Override
    public InvitationResponse getOne(Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Invitation with id " + id + " not found")
        );

        // Weddings 정보 호출 ToDo :: fetch join으로 변경
        invitation.getWeddings().getId();

        return invitationMapper.toDto(invitation);
    }

    @Override
    public Void delete(Long id) {
        Invitation invitation = invitationRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Invitation with id " + id + " not found")
        );
        invitationRepository.delete(invitation);
        return null;
    }

    @Override
    public Long update(InvitationUpdateRequest request) {
        Invitation invitation = invitationRepository.findById(request.getId()).orElseThrow(
                () -> new RuntimeException("Invitation with id " + request.getId() + " not found")
        );


        invitation.setTitle(request.getTitle());
        invitation.setMessage(request.getMessage());
        invitation.setTemplateId(request.getTemplateId());
        invitation.setStatus(request.getStatus());
        invitation.setStartDate(request.getStartDate());
        invitation.setEndDate(request.getEndDate());


        return request.getId();
    }
}
