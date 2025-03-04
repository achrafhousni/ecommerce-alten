package com.alten.shop.security.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.alten.shop.security.provider.TokenProvider;
import com.alten.shop.utils.ExceptionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;



import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationFilter extends OncePerRequestFilter {

	private static final String TOKEN_PREFIX = "Bearer ";
	private static final String HTTP_OPTIONS_METHOD = "OPTIONS";
	private static final String[] PUBLIC_ROUTES = {   "/user/token","/user/account"  };

	 

	private final TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {

			String token = getToken(request);
            String email=getUserEmail(request);
			if (tokenProvider.isTokenValid(email, token)) {
				List<GrantedAuthority> authorities = tokenProvider.getAuthorities(token);

				Authentication authentication = tokenProvider.getAuthentication(email, authorities,
						request);
				SecurityContextHolder.getContext().setAuthentication(authentication);

			} else {
				SecurityContextHolder.clearContext();
			}

			filterChain.doFilter(request, response);
		} catch (Exception e) {
			ExceptionUtil.processError(request, response, e);
			log.error(e.getMessage());
 		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getHeader(HttpHeaders.AUTHORIZATION) == null
				|| !request.getHeader(HttpHeaders.AUTHORIZATION).startsWith(TOKEN_PREFIX)
				|| request.getMethod().equalsIgnoreCase(HTTP_OPTIONS_METHOD)
				|| Arrays.asList(PUBLIC_ROUTES).contains(request.getRequestURI());
	}

	private String getUserEmail(HttpServletRequest request) {
	return   tokenProvider.getSubject(getToken(request), request);
}

	private String getToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
				.filter(header -> header.startsWith(TOKEN_PREFIX))
				.map(token -> token.replace(TOKEN_PREFIX, StringUtils.EMPTY)).get();
	}

}
