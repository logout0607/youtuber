package com.todaywork.security.handler;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaywork.security.service.CmmLoginHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@Component
public class CustomAuthenticationSuccessHandler implements
	AuthenticationSuccessHandler {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ObjectMapper mapper;

	@Autowired
	private Environment environment;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Autowired
	CustomAuthenticationSuccessHandler(MappingJackson2HttpMessageConverter messageConverter) {
		this.mapper = messageConverter.getObjectMapper();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String url = request.getRequestURI();
		logger.debug("URL : {}", url);

		if(url != null && url.startsWith("/auth") && url.contains(";")){
			String[] activeProfile = environment.getActiveProfiles();
			logger.debug("PROFILES IS PROD ? : {}", Arrays.binarySearch(activeProfile, "prod"));
			logger.debug("PROFILES IS SECURITY ? : {}", Arrays.binarySearch(activeProfile, "security"));
			String redirectCode = url.substring(url.indexOf(";") + 1);
			logger.debug("REDIRECT_CODE : {}", redirectCode);
			switch (redirectCode){
				case "public" :
					if(Arrays.binarySearch(activeProfile, "security") > -1) {
						redirectStrategy.sendRedirect(request, response, "http://local.carlab.co.kr:3000/login-success");
					} else if(Arrays.binarySearch(activeProfile, "prod") > -1) {
						redirectStrategy.sendRedirect(request, response, "http://www.carlab.co.kr/login-success");
					}
					return;
			}
		}

		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType("application/json");

		mapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

		PrintWriter writer = response.getWriter();
		String result = mapper.writeValueAsString(CmmLoginHelper.getUser());
		logger.debug("HEADER : {}", response.getHeaderNames());
		logger.debug("LOGIN SUCCESS : {}", request);
		writer.append(result);
		writer.flush();
	}
}