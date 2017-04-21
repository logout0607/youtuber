package com.todaywork.domain.code;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
public enum Nationality {
    LOCAL("내국인"), FOREIGNER("외국인");

    private String value;

    Nationality(String value) {
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
