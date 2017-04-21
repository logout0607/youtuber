package com.todaywork.employment;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaywork.domain.Employment;
import com.todaywork.employment.service.EmploymentService;
import com.todaywork.employment.service.impl.EmploymentDAO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.expression.ParseException;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

/**
 * Created by 권성봉 on 2016. 11. 14..
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EmploymentControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmploymentController employmentController;

    @Autowired
    private EmploymentService employmentService;

    @Autowired
    private EmploymentDAO employmentDAO;

    @Before
    public void setUp() throws ParseException {
        this.mockMvc = MockMvcBuilders.standaloneSetup(employmentController).setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
    }



    public Employment lastOne(){
        List<Employment> employmentList = (List<Employment>)employmentDAO.findAll();

        if(employmentList.size() > 0){
            return employmentList.get(employmentList.size() - 1);
        }
        return null;
    }

}
