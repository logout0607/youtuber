package com.todaywork.domain.code;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
public enum WorkArea {
    TRANSPORT("운반"), ASSIST("보조");

    private String value;

    WorkArea(String value) {
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
