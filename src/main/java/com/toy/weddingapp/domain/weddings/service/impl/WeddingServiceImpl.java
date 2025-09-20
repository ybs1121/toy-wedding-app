package com.toy.weddingapp.domain.weddings.service.impl;

import com.toy.weddingapp.domain.users.entity.User;
import com.toy.weddingapp.domain.users.repository.UserRepository;
import com.toy.weddingapp.domain.weddings.dto.WeddingAddRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingModRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingResponse;
import com.toy.weddingapp.domain.weddings.entity.Weddings;
import com.toy.weddingapp.domain.weddings.mapper.WeddingMapper;
import com.toy.weddingapp.domain.weddings.repository.WeddingRepository;
import com.toy.weddingapp.domain.weddings.service.WeddingService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class WeddingServiceImpl implements WeddingService {

    private final WeddingRepository weddingRepository;
    private final UserRepository userRepository;
    private final WeddingMapper weddingMapper;
    private final EntityManager em;

    @Override
    public Long save(WeddingAddRequest weddingAddRequest) {
        User user = userRepository.findByUserId(weddingAddRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

//        User referenceUser = em.getReference(User.class, user.getId());

        Weddings saveWeddings = weddingRepository.save(weddingMapper.toEntity(weddingAddRequest, user));

        return saveWeddings.getId();
    }

    @Override
    public Long update(long id, WeddingModRequest weddingModRequest) {
        Weddings findWedding = weddingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wedding not found"));
        // 필드별 변경 적용
        findWedding.setTitle(weddingModRequest.getTitle());
        findWedding.setLocation(weddingModRequest.getLocation());
        findWedding.setWeddingDate(weddingModRequest.getWeddingDate());
        findWedding.setGroomName(weddingModRequest.getGroomName());
        findWedding.setBrideName(weddingModRequest.getBrideName());

        return id;
    }

    @Override
    public WeddingResponse findOne(long id) {
        Weddings findWedding = weddingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Wedding not found"));
        return weddingMapper.toDTO(findWedding);
    }

}
