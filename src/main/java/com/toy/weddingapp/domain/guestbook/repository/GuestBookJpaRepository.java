package com.toy.weddingapp.domain.guestbook.repository;

import com.toy.weddingapp.domain.guestbook.entity.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestBookJpaRepository extends JpaRepository<GuestBook, Long> {
}
