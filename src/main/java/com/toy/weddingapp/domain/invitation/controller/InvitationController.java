package com.toy.weddingapp.domain.invitation.controller;

import com.toy.weddingapp.domain.invitation.dto.InvitationAddRequest;
import com.toy.weddingapp.domain.invitation.dto.InvitationResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationStatusStatisticsResponse;
import com.toy.weddingapp.domain.invitation.dto.InvitationUpdateRequest;
import com.toy.weddingapp.domain.invitation.service.InvitationService;
import io.seruco.encoding.base62.Base62;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/invite")
public class InvitationController {

    private final InvitationService invitationService;


    @PostMapping
    public Long save(@Valid @RequestBody InvitationAddRequest request) {
        return invitationService.save(request);
    }

    @GetMapping("/{id}")
    public InvitationResponse getInvitation(@PathVariable Long id) {
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

    @PatchMapping("/{id}/uri")
    public InvitationResponse createShortUri(@PathVariable Long id) {
        return invitationService.createUrl(id);
    }

    @GetMapping("/{shortUri}")
    public ResponseEntity<?> getInvitationByShortUri(@PathVariable String shortUri) {
        String id = new String(Base62.createInstance().decode(shortUri.getBytes(StandardCharsets.UTF_8)));
        String longUrl = invitationService.getOne(Long.parseLong(id)).getUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(longUrl));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }


    @GetMapping("/status")
    public InvitationStatusStatisticsResponse getInvitationStatusStatistics() {
        return invitationService.getInvitationStatusStatistics();
    }

    @GetMapping
    public List<InvitationResponse> searchInvitations() {
        return invitationService.searchInvitations();
    }




}
