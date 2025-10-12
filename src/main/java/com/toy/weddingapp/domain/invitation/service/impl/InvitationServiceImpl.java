package com.toy.weddingapp.domain.invitation.service.impl;

import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationStatusStatisticsResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationUpdateRequest;
import com.toy.weddingapp.domain.invitation.entity.Invitation;
import com.toy.weddingapp.domain.invitation.mapper.InvitationMapper;
import com.toy.weddingapp.domain.invitation.repository.InvitationJpaRepository;
import com.toy.weddingapp.domain.invitation.repository.InvitationQuerydslRepository;
import com.toy.weddingapp.domain.invitation.service.InvitationService;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import com.toy.weddingapp.domain.weddings.repository.WeddingRepository;
import io.seruco.encoding.base62.Base62;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl implements InvitationService {

    private final InvitationMapper invitationMapper;
    private final InvitationJpaRepository invitationJpaRepository;
    private final InvitationQuerydslRepository invitationQuerydslRepository;
    private final WeddingRepository weddingRepository;
    private final EntityManager em;

    @Value("${weddings.domain}")
    private String DOMAIN;

    @Override
    public Long save(InvitationAddRequest request) {
        Weddings weddingsRef = em.getReference(Weddings.class, request.getWeddingsId());
        Invitation saveInvitation = invitationJpaRepository.save(invitationMapper.toEntity(request, weddingsRef));
        return saveInvitation.getId();
    }

    @Override
    public InvitationResponse getOne(Long id) {
        Invitation invitation = invitationJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Invitation with id " + id + " not found")
        );

        // Weddings 정보 호출 ToDo :: fetch join으로 변경
        invitation.getWeddings().getId();

        return invitationMapper.toDto(invitation);
    }

    @Override
    public Void delete(Long id) {
        Invitation invitation = invitationJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Invitation with id " + id + " not found")
        );
        invitationJpaRepository.delete(invitation);
        return null;
    }

    @Override
    public Long update(InvitationUpdateRequest request) {
        Invitation invitation = invitationJpaRepository.findById(request.getId()).orElseThrow(
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

    @Override
    public InvitationResponse createUrl(Long id) {
        String shortUri = new String(Base62.createInstance().encode(longToBytes(id)));

        Invitation invitation = invitationJpaRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Invitation with id " + id + " not found")
        );
        invitation.setShortUrl(String.format("%s/invitations/view/%s", DOMAIN, shortUri));
        invitation.setUrl(String.format("%s/invitations/view/%s", DOMAIN, id));
        return invitationMapper.toDto(invitation);
    }

    @Override
    public InvitationStatusStatisticsResponse getInvitationStatusStatistics() {
        return invitationQuerydslRepository.getInvitationStatusStatistics();
    }

    @Override
    public List<InvitationResponse> searchInvitations() {
        return invitationQuerydslRepository.searchInvitations();
    }


    public static byte[] longToBytes(long x) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(x);
        return buffer.array();
    }

}
