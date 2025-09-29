package com.toy.weddingapp.common.constant.convert;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public abstract class CodeEnumConverter<T extends CodeEnum> implements AttributeConverter<T, String> {

    private final Class<T> clazz;

    public CodeEnumConverter(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String convertToDatabaseColumn(T value) {
        return value == null ? null : value.getValue();
    }

    @Override
    public T convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }

        for (T enumConstant : clazz.getEnumConstants()) {
            if (enumConstant.getValue().equals(value)) {
                return enumConstant;
            }
        }
        throw new IllegalArgumentException("Unknown database value: " + value);
    }

}

