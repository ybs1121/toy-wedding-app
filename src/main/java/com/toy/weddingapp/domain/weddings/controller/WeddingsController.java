package com.toy.weddingapp.domain.weddings.controller;

import com.toy.weddingapp.domain.weddings.dto.WeddingAddRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingModRequest;
import com.toy.weddingapp.domain.weddings.dto.WeddingResponse;
import com.toy.weddingapp.domain.weddings.service.WeddingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/weddings")
@RequiredArgsConstructor
@RestController
public class WeddingsController {

    private final WeddingService weddingService;

    @PostMapping
    public Long save(@Valid @RequestBody WeddingAddRequest weddingAddRequest) {
        return weddingService.save(weddingAddRequest);
    }

    @PutMapping("/{id}")
    public Long update(@Valid @RequestBody WeddingModRequest weddingModRequest,
                       @PathVariable long id) {
        return weddingService.update(id, weddingModRequest);
    }

    @GetMapping("/{id}")
    public WeddingResponse findOne(@PathVariable long id) {
        return weddingService.findOne(id);
    }
}
