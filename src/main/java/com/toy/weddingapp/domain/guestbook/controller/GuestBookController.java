package com.toy.weddingapp.domain.guestbook.controller;

import com.toy.weddingapp.domain.guestbook.dto.GuestBookAddRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookModRequest;
import com.toy.weddingapp.domain.guestbook.dto.GuestBookResponse;
import com.toy.weddingapp.domain.guestbook.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/guest-book")
public class GuestBookController {

    private final GuestBookService guestBookService;


    @PostMapping
    public Long save(@RequestBody GuestBookAddRequest addRequest) {
        return guestBookService.save(addRequest); // 성공 시 저장된 GuestBook ID 반환
    }

    @GetMapping("/{id}")
    public GuestBookResponse findOne(@PathVariable Long id) {
        return guestBookService.findOne(id);
    }


    @PutMapping("/{id}")
    public GuestBookResponse update(@PathVariable Long id, @RequestBody GuestBookModRequest modRequest) {
        modRequest.setId(id);
        return guestBookService.update(modRequest);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        guestBookService.delete(id);
    }
}
