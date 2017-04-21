package com.todaywork;

import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.joda.ser.DateTimeSerializer;
import com.fasterxml.jackson.datatype.joda.ser.JacksonJodaFormat;
import com.todaywork.domain.User;
import com.todaywork.dto.UserDto;
import com.todaywork.dto.UserRoleDto;
import com.todaywork.user.service.impl.UserDAO;
import org.joda.time.DateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.joda.DateTimeFormatterFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 권 오빈 on 2016. 6. 2..
 */
@SpringBootApplication(scanBasePackages = "com.todaywork")
@EnableTransactionManagement(proxyTargetClass = true)
@EnableSpringDataWebSupport
@EnableJpaRepositories
@EnableJpaAuditing
@EnableScheduling
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400)
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class Application extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<Application> applicationClass = Application.class;

	/*@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private DataSource dataSource;*/

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new JpaTransactionManager(entityManagerFactory);
	}

	@Bean
	public Jackson2ObjectMapperBuilder jacksonBuilder() {
		Jackson2ObjectMapperBuilder b = new Jackson2ObjectMapperBuilder();
		b.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		return b;
	}

	@Bean
	public JodaModule jacksonJodaModule() {
		JodaModule module = new JodaModule();
		DateTimeFormatterFactory formatterFactory = new DateTimeFormatterFactory();
		formatterFactory.setIso(DateTimeFormat.ISO.DATE);
		module.addSerializer(DateTime.class, new DateTimeSerializer(
			new JacksonJodaFormat(formatterFactory.createDateTimeFormatter()
				.withZoneUTC())));
		return module;
	}
//
//	@Bean
//	public WebMvcConfigurer corsConfigurer(){
//		return new WebMvcConfigurerAdapter() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**").allowedOrigins("http://admin.dev-fsa.carlab.co.kr");
//			}
//		};
//	}

	@Resource(name = "userDAO")
	private UserDAO userDAO;

	@Autowired private ModelMapper modelMapper;

	@Autowired private BCryptPasswordEncoder passwordEncoder;

	@Configuration
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.authorizeRequests()
				.anyRequest().permitAll()
				.and()
				.csrf().disable()
				.headers()
				.frameOptions().disable()
				.and()
				.anonymous();
		}
	}

	/**
	 * 테스트 용 초기 데이터 중 User 부분 생성
	 * @return
	 */
	@Profile("local")
	@Bean
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public InitializingBean localInitializingBean(){
		return () -> {
			if(ObjectUtils.isEmpty(userDAO.findByEmail("test-init@test.co.kr"))){
				UserDto.Create userCreate = new UserDto.Create();
				userCreate.setEmail("test-init@test.co.kr");
				userCreate.setUserName("테스트");
				userCreate.setPassword(passwordEncoder.encode("test"));

				UserRoleDto.Create userRoleCreate = new UserRoleDto.Create();
				userRoleCreate.setRole("ROLE_ADMIN");

				List<UserRoleDto.Create> userRoleList = new ArrayList<>();
				userRoleList.add(userRoleCreate);

				userCreate.setUserRoleList(userRoleList);

				userRoleCreate = new UserRoleDto.Create();
				userRoleCreate.setRole("ROLE_USER");

				userRoleList.add(userRoleCreate);

				userCreate.setUserRoleList(userRoleList);

				User user = modelMapper.map(userCreate, User.class);
				//user.setCreatedDate(new DateTime());
				//user.setLastModifiedDate(new DateTime());
				user.getUserRoleList().stream().forEach(userRole -> userRole.setUser(user));
				userDAO.save(user);
			}
		};
	}

	@Profile("!local")
	@Bean
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public InitializingBean initializingBean(){
		return () -> {
			if(ObjectUtils.isEmpty(userDAO.findByEmail("foryourstar@foryourstar.com"))){
				UserDto.Create userCreate = new UserDto.Create();
				userCreate.setEmail("foryourstar@foryourstar.com");
				userCreate.setUserName("관리자");
				userCreate.setPassword(passwordEncoder.encode("1234"));

				UserRoleDto.Create userRoleCreate = new UserRoleDto.Create();
				userRoleCreate.setRole("ROLE_ADMIN");

				List<UserRoleDto.Create> userRoleList = new ArrayList<>();
				userRoleList.add(userRoleCreate);

				userCreate.setUserRoleList(userRoleList);

				userRoleCreate = new UserRoleDto.Create();
				userRoleCreate.setRole("ROLE_USER");

				userRoleList.add(userRoleCreate);

				User user = modelMapper.map(userCreate, User.class);
				//user.setCreatedDate(new DateTime());
				//user.setLastModifiedDate(new DateTime());
				user.getUserRoleList().stream().forEach(userRole -> userRole.setUser(user));

				System.setProperty("defaultUserUniqueId", userDAO.save(user).getUniqueId().toString());
			}

			if(ObjectUtils.isEmpty(userDAO.findByEmail("default@foryourstar.com"))){
				UserDto.Create userCreate = new UserDto.Create();
				userCreate.setEmail("default@foryourstar.com");
				userCreate.setUserName("Default");
				userCreate.setPassword("Default");

				User user = modelMapper.map(userCreate, User.class);
				//user.setCreatedDate(new DateTime());
				//user.setLastModifiedDate(new DateTime());
				userDAO.save(user);
			}
		};
	}

	@Bean
	public ScheduledExecutorFactoryBean scheduledExecutorService() {

		ScheduledExecutorFactoryBean bean = new ScheduledExecutorFactoryBean();
		bean.setPoolSize(5);

		return bean;
	}
}
