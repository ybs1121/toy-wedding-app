package com.toy.weddingapp.domain.invitation.repository;

import com.toy.weddingapp.domain.invitation.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Invitation findByShortUrl(String shortUrl);
}
