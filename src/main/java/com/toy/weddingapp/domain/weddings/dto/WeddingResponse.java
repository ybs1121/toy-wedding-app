package com.toy.weddingapp.domain.weddings.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WeddingResponse {

    private String userId;


    private String title;


    private String location;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime weddingDate;

    private String groomName;

    private String brideName;
}
