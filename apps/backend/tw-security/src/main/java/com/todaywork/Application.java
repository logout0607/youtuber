package com.todaywork;

import com.todaywork.dto.UserDto;
import com.todaywork.security.CustomAuthenticationEntryPoint;
import com.todaywork.security.filter.StatelessLoginFilter;
import com.todaywork.security.handler.CustomAuthenticationFailureHandler;
import com.todaywork.security.handler.CustomAuthenticationSuccessHandler;
import com.todaywork.security.handler.CustomLogoutSuccessHandler;
import com.todaywork.security.service.CmmLoginHelper;
import com.todaywork.security.service.CmmSocialAndUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.persistence.EntityManagerFactory;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringBootApplication(scanBasePackages = "com.todaywork")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 86400)
@EnableTransactionManagement
@EnableConfigurationProperties
@RestController
public class Application extends SpringBootServletInitializer {

	/*@Autowired
	private ProviderSignInUtils providerSignInUtils;*/

	@SuppressWarnings("SpringJavaAutowiringInspection")
	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Bean
	public PlatformTransactionManager transactionManager() {
		PlatformTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
		return transactionManager;
	}

	@RequestMapping("/")
	public CsrfToken main(CsrfToken token){
		return token;
	}

	@RequestMapping("/me")
	public UserDto.Session me(){
		return CmmLoginHelper.getUser();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(applicationClass);
	}

//	@Bean
//	public HttpSessionStrategy httpSessionStrategy(){
//		return new HeaderHttpSessionStrategy();
//	}

//	@Bean
//	public CookieSerializer cookieSerializer() {
//		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
//		serializer.setCookieName("SESSION");
//		serializer.setCookiePath("/");
//		serializer.setDomainNamePattern("[^\\\\.]+\\\\.[a-z]+$");
//		return serializer;
//	}

	private static Class<Application> applicationClass = Application.class;

	@Configuration
	@EnableWebSecurity
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	protected static class SecurityConfiguration extends WebSecurityConfigurerAdapter {

		@Autowired
		private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
		@Autowired
		private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
		@Autowired
		private CustomLogoutSuccessHandler customLogoutSuccessHandler;

		@Autowired
		private CmmSocialAndUserDetailService userDetailsService;

		@Autowired
		private AuthenticationProvider customAuthProvider;

		@Autowired
		private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

		@Autowired
		public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
			// @formatter:off
			auth
				.authenticationProvider(customAuthProvider);
			// @formatter:on
		}

//		private CookieCsrfTokenRepository tokenRepository(){
//			CookieCsrfTokenRepository tokenRepository = new CookieCsrfTokenRepository();
//			tokenRepository.setCookieHttpOnly(false);
//			return tokenRepository;
//		}

//		@Override
//		public void configure(WebSecurity web) throws Exception {
//			//super.configure(web);
//			web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
//		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			StatelessLoginFilter statelessLoginFilter = new StatelessLoginFilter("/login", customAuthProvider);
			statelessLoginFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
			statelessLoginFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);

			/*final SpringSocialConfigurer socialConfigurer = new SpringSocialConfigurer();
			socialConfigurer.addObjectPostProcessor(new ObjectPostProcessor<SocialAuthenticationFilter>() {
				@Override
				public <O extends SocialAuthenticationFilter> O postProcess(O socialAuthenticationFilter) {
					socialAuthenticationFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
					return socialAuthenticationFilter;
				}
			});*/

			http
				.formLogin()
					.loginProcessingUrl("/login")
					.usernameParameter("email")
					.passwordParameter("password")
					.successHandler(customAuthenticationSuccessHandler)
					.failureHandler(customAuthenticationFailureHandler)
				.and()
					.authenticationProvider(customAuthProvider)
					.exceptionHandling()
					.authenticationEntryPoint(customAuthenticationEntryPoint)
				.and()
					.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
						.logoutSuccessHandler(customLogoutSuccessHandler)
				.and()
					.authorizeRequests()
					.antMatchers("/login", "/", "/auth", "/auth/me", "/connect/**", "/auth/connect/**", "/auth/**", "/signup", "/auth/signup").permitAll()
					.anyRequest().authenticated()
				.and()
					.csrf().csrfTokenRepository(csrfTokenRepository())
					//.csrf().csrfTokenRepository(tokenRepository())
//				.and()
//					.cors()
//						.configurationSource(configurationSource())
				.and()
					.addFilterBefore(statelessLoginFilter, UsernamePasswordAuthenticationFilter.class);
					//.addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class);
				/*.and()
					.apply(socialConfigurer)*/
		}

		private Filter csrfHeaderFilter() {
			return new OncePerRequestFilter() {
				@Override
				protected void doFilterInternal(HttpServletRequest request,
												HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
					CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
						.getName());
					if (csrf != null) {
						Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
						String token = csrf.getToken();
						if (cookie == null || token != null
							&& !token.equals(cookie.getValue())) {
							cookie = new Cookie("XSRF-TOKEN", token);
							cookie.setPath("/");
							response.addCookie(cookie);
						}
					}
					filterChain.doFilter(request, response);
				}
			};
		}

		private CsrfTokenRepository csrfTokenRepository() {
			HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
			repository.setHeaderName("X-XSRF-TOKEN");
			return repository;
		}
	}
}