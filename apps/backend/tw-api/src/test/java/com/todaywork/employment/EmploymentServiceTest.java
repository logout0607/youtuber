package com.todaywork.employment;

import com.todaywork.domain.Company;
import com.todaywork.domain.Employment;
import com.todaywork.domain.code.Gender;
import com.todaywork.domain.code.Nationality;
import com.todaywork.domain.code.WorkArea;
import com.todaywork.dto.EmploymentDto;
import com.todaywork.employment.service.EmploymentService;
import com.todaywork.employment.service.impl.CompanyDAO;
import com.todaywork.employment.service.impl.EmploymentDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertSame;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmploymentServiceTest {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private EmploymentDAO employmentDAO;

    @Autowired
    private CompanyDAO companyDAO;

    private Employment employment;

    private Company company;

    @Before
    public void setUp() throws ParseException {
        Company company = lastCompany();
        if(company == null) {
            company.setName("회사");
            company.setCategory("건설");
            this.company = companyDAO.save(company);
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String startDateString = "2016-04-05 08:50";
        Date startDate = format.parse(startDateString);
        String endDateString = "2016-04-05 14:50";
        Date endDate = format.parse(endDateString);

        employment = new Employment();
        employment.setCompany(company);
        employment.setStartDailyPay(50000);
        employment.setEndDailyPay(80000);
        employment.setWorkAreaList(Arrays.asList(WorkArea.ASSIST, WorkArea.TRANSPORT));
        employment.setGender(Gender.MALE);
        employment.setNationality(Nationality.LOCAL);
        employment.setStartDate(startDate);
        employment.setEndDate(endDate);
        employment.setAddress("서울 서초구 방배동");
        employment.setDetails("details");
        employment.setDetails("address");
    }

    @Test
    public void test(){
        for(int i=0; i<50; i++){
            employment.setDetails("details" + i);
            employment.setDetails("address" + i);
            추가하기();
        }
    }

    @Test
    public void 추가하기(){
        EmploymentDto.Create employmentDto = modelMapper.map(employment, EmploymentDto.Create.class);

        int result = employmentService.create(employmentDto);

        assertSame(1, result);
    }


    @Test
    public void 수정하기(){
        EmploymentDto.Update employmentDto = modelMapper.map(lastOne(), EmploymentDto.Update.class);

        int result = employmentService.update(employmentDto);

        assertSame(1, result);
    }

    @Test
    public void 삭제하기(){

    }

    @Test
    public void 목록가져오기(){
        Page<EmploymentDto.Response> list = employmentService.findAll(new PageRequest(0, 3));
    }

    @Test
    public void 한개가져오기(){
        Employment employment = lastOne();

        EmploymentDto.ResponseOne one = employmentService.findOne(employment.getIdx());
    }

    public Employment lastOne(){
        List<Employment> employmentList = (List<Employment>)employmentDAO.findAll();

        if(employmentList.size() > 0){
            return employmentList.get(employmentList.size() - 1);
        }
        return null;
    }

    public Company lastCompany(){
        List<Company> companyList = (List<Company>)companyDAO.findAll();

        if(companyList.size() > 0){
            return companyList.get(companyList.size() - 1);
        }
        return null;
    }

}
