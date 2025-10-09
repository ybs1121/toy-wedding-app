package com.toy.weddingapp.domain.guestbook.service;

import com.toy.weddingapp.domain.guestbook.dto.GuestBookAddRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookModRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookResponse;

public interface GuestBookService {

    Long save(GuestBookAddRequest addRequest);

    GuestBookResponse findOne(Long id);

    GuestBookResponse update(GuestBookModRequest modRequest);

    void delete(Long id);
}
