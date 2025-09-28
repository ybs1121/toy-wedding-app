package com.toy.weddingapp.common.constant.convert;

import com.toy.weddingapp.common.constant.Status;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatusConverter extends CodeEnumConverter<Status> {
    public StatusConverter() {
        super(Status.class);
    }
}
