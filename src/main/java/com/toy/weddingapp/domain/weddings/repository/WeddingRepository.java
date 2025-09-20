package com.toy.weddingapp.domain.weddings.repository;

import com.toy.weddingapp.domain.weddings.entity.Weddings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeddingRepository extends JpaRepository<Weddings, Long> {
}
