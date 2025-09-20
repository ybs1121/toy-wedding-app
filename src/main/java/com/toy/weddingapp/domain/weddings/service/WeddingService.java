package com.toy.weddingapp.domain.weddings.service;

import com.toy.weddingapp.domain.weddings.dto.WeddingAddRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingModRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingResponse;

public interface WeddingService {
    Long save(WeddingAddRequest weddingAddRequest);

    Long update(long id, WeddingModRequest weddingModRequest);

    WeddingResponse findOne(long id);
}
