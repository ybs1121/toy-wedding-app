package com.toy.weddingapp.common.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.toy.weddingapp.common.constant.convert.CodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Status implements CodeEnum {

    WAITING("STS001", "대기"),
    PROGRESS("STS002", "진행중"),
    CLOSED("ST003", "만료");

    @JsonValue
    private String value;
    private String desc;


    @JsonCreator
    public static Status fromValue(String value) {
        for (Status status : Status.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
