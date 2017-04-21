package com.todaywork.dto;

import com.todaywork.domain.code.Gender;
import com.todaywork.domain.code.Nationality;
import com.todaywork.domain.code.WorkArea;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
public class EmploymentDto {

    @Data
    public static class Response {
        private long idx;
        private long startDailyPay;
        private long endDailyPay;
        private Gender gender;
        private Nationality nationality;
        private Date startDate;
        private Date endDate;
        private String address;
        private String details;
        private int recruitingPersonnel;
        private List<WorkArea> workAreaList;
        private CompanyDto.Response company;
        private ImageGroupDto.Response imageGroup;
    }

    @Data
    public static class ResponseOne {
        private long idx;
        private long startDailyPay;
        private long endDailyPay;
        private Gender gender;
        private Nationality nationality;
        private Date startDate;
        private Date endDate;
        private String address;
        private String details;
        private int recruitingPersonnel;
        private List<WorkArea> workAreaList;
        private CompanyDto.Response company;
        private ImageGroupDto.Response imageGroup;
    }

    @Data
    public static class Create {
        private long idx;
        private long startDailyPay;
        private long endDailyPay;
        private Gender gender;
        private Nationality nationality;
        private Date startDate;
        private Date endDate;
        private String address;
        private String details;
        private int recruitingPersonnel;
        private List<WorkArea> workAreaList;
        private CompanyDto.Response company;
        private ImageGroupDto.Create imageGroup;
    }

    @Data
    public static class Update {
        private long idx;
        private long startDailyPay;
        private long endDailyPay;
        private Gender gender;
        private Nationality nationality;
        private Date startDate;
        private Date endDate;
        private String address;
        private String details;
        private int recruitingPersonnel;
        private List<WorkArea> workAreaList;
        private CompanyDto.Response company;
        private ImageGroupDto.Update imageGroup;
    }

    @Data
    public static class Delete {
        private long idx;
    }

}