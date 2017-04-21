package com.todaywork.dto;

import lombok.Data;

/**
 * Created by 권성봉 on 2016. 11. 15..
 */
public class CompanyDto {

    @Data
    public static class Response {
        private long idx;
        private String name;
        private String category;
    }

}
