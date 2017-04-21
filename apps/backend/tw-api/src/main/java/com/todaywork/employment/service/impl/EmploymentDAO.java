package com.todaywork.employment.service.impl;

import com.todaywork.domain.Employment;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
@Repository("employmentDAO")
public interface EmploymentDAO extends PagingAndSortingRepository<Employment, Long> {
}
