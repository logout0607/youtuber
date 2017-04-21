package com.todaywork.domain.code;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
public enum Gender {
    FEMALE("여자"), MALE("남자");

    private String value;

    Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @JsonValue
    public String getCode(){
        return this.name();
    }
}
