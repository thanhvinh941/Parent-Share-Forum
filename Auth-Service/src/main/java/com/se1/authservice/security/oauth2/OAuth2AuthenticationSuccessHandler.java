package com.se1.authservice.security.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.se1.authservice.config.AppProperties;
import com.se1.authservice.exception.BadRequestException;
import com.se1.authservice.model.User;
import com.se1.authservice.payload.AuthResponse;
import com.se1.authservice.payload.UserDetail;
import com.se1.authservice.security.TokenProvider;
import com.se1.authservice.security.UserPrincipal;
import com.se1.authservice.service.UserDetailService;
import com.se1.authservice.util.CookieUtils;

import io.jsonwebtoken.security.InvalidKeyException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";

	private TokenProvider tokenProvider;
	private UserDetailService userService;
	private AppProperties appProperties;

	private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

	@Autowired
	OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, AppProperties appProperties,
			HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
		this.tokenProvider = tokenProvider;
		this.appProperties = appProperties;
		this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		Optional<String> redirectUri = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
				.map(Cookie::getValue);

		if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
			throw new BadRequestException(
					"Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
		}

		String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
		User user = null;
		try {
			user = userService.findByEmail(userPrincipal.getEmail()).orElse(null);
		} catch (JsonProcessingException e1) {
			e1.printStackTrace();
		}
		UserDetail userDetail = new UserDetail();
		BeanUtils.copyProperties(user, userDetail);
		AuthResponse authResponse = null;
		try {
			authResponse = tokenProvider.createToken(userPrincipal.getEmail(), userDetail);
		} catch (InvalidKeyException | JsonProcessingException e) {
			e.printStackTrace();
		}

		return UriComponentsBuilder.fromUriString(targetUrl).queryParam("token", authResponse.getAccessToken())
				.queryParam("user_email", authResponse.getEmail())
				.queryParam("expiry_date", authResponse.getExpiryDate().getTime()).build().toUriString();
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);

		return appProperties.getOauth2().getAuthorizedRedirectUris().stream().anyMatch(authorizedRedirectUri -> {
			// Only validate host and port. Let the clients use different paths if they want
			// to
			URI authorizedURI = URI.create(authorizedRedirectUri);
			if (authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
					&& authorizedURI.getPort() == clientRedirectUri.getPort()) {
				return true;
			}
			return false;
		});
	}
}
