package com.todaywork.employment.service;

import com.todaywork.dto.EmploymentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
public interface EmploymentService {

    Page<EmploymentDto.Response> findAll(Pageable pageable);

    EmploymentDto.ResponseOne findOne(Long idx);

    int create(EmploymentDto.Create employment);

    int update(EmploymentDto.Update employment);

    int remove(Long idx);

}
