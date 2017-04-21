package com.todaywork.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaywork.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 권 오빈 on 2016. 3. 6..
 */
public class StatelessLoginFilter extends AbstractAuthenticationProcessingFilter {

	private AuthenticationProvider customAuthProvider;

	private static final Logger logger = LoggerFactory.getLogger(StatelessLoginFilter.class);

	public StatelessLoginFilter(String defaultFilterProcessesUrl, AuthenticationProvider customAuthProvider) {
		super(defaultFilterProcessesUrl);
		this.customAuthProvider = customAuthProvider;
	}

//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) res;
//		logger.debug("request method : {}", request.getMethod());
//		if(HttpMethod.OPTIONS.matches(request.getMethod())) {
//			logger.debug("NOT CHAIN");
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//		}else{
//			logger.debug("CHAIN");
//			chain.doFilter(req, res);
//		}
//	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
		logger.debug("request method : {}", request.getMethod());
//		if(HttpMethod.OPTIONS.matches(request.getMethod())){
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//			return null;
//		}
		final User user = new ObjectMapper().readValue(request.getInputStream(), User.class);

		logger.debug("StatelessLoginFilter User Email : {}", user.getEmail());

		final UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
		return customAuthProvider.authenticate(userToken);
	}
}
