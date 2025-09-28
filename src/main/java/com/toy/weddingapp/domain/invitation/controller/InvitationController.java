package com.toy.weddingapp.domain.invitation.controller;

import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationUpdateRequest;
import com.toy.weddingapp.domain.invitation.service.InvitationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class InvitationController {

    private final InvitationService invitationService;


    @PostMapping
    public Long save(@Valid @RequestBody InvitationAddRequest request) {
        return invitationService.save(request);
    }

    @GetMapping
    public InvitationResponse getInvitation(Long id) {
        return invitationService.getOne(id);
    }

    @DeleteMapping
    public Void delete(Long id) {
        return invitationService.delete(id);
    }

    @PutMapping("/{id}")
    public Long update(@Valid @RequestBody InvitationUpdateRequest request,
                       @PathVariable Long id) {
        request.setId(id);
        return invitationService.update(request);
    }
}
