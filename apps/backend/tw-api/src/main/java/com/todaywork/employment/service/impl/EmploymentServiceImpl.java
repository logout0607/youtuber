package com.todaywork.employment.service.impl;

import com.todaywork.domain.Employment;
import com.todaywork.dto.EmploymentDto;
import com.todaywork.employment.service.EmploymentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
@Service("employmentService")
public class EmploymentServiceImpl implements EmploymentService {

    @Autowired
    private ModelMapper modelMapper;

    @Resource(name="employmentDAO")
    private EmploymentDAO employmentDAO;


    @Override
    @Transactional
    public Page<EmploymentDto.Response> findAll(Pageable pageable) {
        Page<Employment> page = employmentDAO.findAll(pageable);
        List<EmploymentDto.Response> content = page.getContent().stream().map(employment -> modelMapper.map(employment, EmploymentDto.Response.class)).collect(Collectors.toList());
        return new PageImpl<>(content, pageable, page.getTotalElements());
    }

    @Override
    @Transactional
    public EmploymentDto.ResponseOne findOne(Long idx) {
        return modelMapper.map(employmentDAO.findOne(idx), EmploymentDto.ResponseOne.class);
    }

    @Override
    @Transactional
    public int create(EmploymentDto.Create employment) {
        Employment result = modelMapper.map(employment, Employment.class);

        employmentDAO.save(result);

        if(!ObjectUtils.isEmpty(result)){
            return 1;
        }

        return 0;

    }

    @Override
    public int update(EmploymentDto.Update employment) {
        Employment result = modelMapper.map(employment, Employment.class);

        employmentDAO.save(result);

        if(!ObjectUtils.isEmpty(result)){
            return 1;
        }

        return 0;
    }

    @Override
    public int remove(Long idx) {
        if(employmentDAO.exists(idx)){
            employmentDAO.delete(idx);
            return 1;
        }

        return 0;
    }
}
