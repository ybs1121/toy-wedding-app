package com.toy.weddingapp.domain.weddings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeddingAddRequest {

    @NotBlank
    @Size(min = 1, max = 50)
    private String userId;

    @NotBlank
    @Size(min = 1, max = 255)
    private String title;

    @NotBlank
    @Size(min = 1, max = 255)
    private String location;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime weddingDate;

    @NotBlank
    @Size(min = 1, max = 50)
    private String groomName;

    @NotBlank
    @Size(min = 1, max = 50)
    private String brideName;

}
