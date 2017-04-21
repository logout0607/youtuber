package com.todaywork.employment;

import com.todaywork.domain.code.EnumEnumerator;
import com.todaywork.domain.code.WorkArea;
import com.todaywork.dto.EmploymentDto;
import com.todaywork.employment.service.EmploymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
@RestController
@RequestMapping("/employment")
public class EmploymentController {

    @Resource(name = "employmentService")
    private EmploymentService employmentService;

    @GetMapping
    public Page<EmploymentDto.Response> findAll(@PageableDefault(sort = "idx", direction = Sort.Direction.DESC) Pageable pageable){
        return employmentService.findAll(pageable);
    }

    @GetMapping("/{idx}")
    public EmploymentDto.ResponseOne findOne(@PathVariable("idx") long idx){
        return employmentService.findOne(idx);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public int create(@RequestBody EmploymentDto.Create employment){
        return employmentService.create(employment);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int update(@RequestBody EmploymentDto.Update employment){
        return employmentService.update(employment);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int delete(@RequestBody EmploymentDto.Delete employment){
        return employmentService.remove(employment.getIdx());
    }

    @RequestMapping(value = "/work-area", method = RequestMethod.GET)
    public List<Map<String, String>> findWorkAreaCodeList() {
        return new EnumEnumerator<>(WorkArea.class).getCodeList();
    }

}
