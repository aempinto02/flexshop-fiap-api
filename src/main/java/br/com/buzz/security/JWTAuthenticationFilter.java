package br.com.buzz.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.buzz.dto.AuthDTO;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authManager;
	private JWTUtil jwtUtil;

	public JWTAuthenticationFilter(AuthenticationManager authManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authManager = authManager;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
			throws AuthenticationException {
		try {
			AuthDTO creds = new ObjectMapper().readValue(req.getInputStream(), AuthDTO.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), new ArrayList<>());

			Authentication auth = authManager.authenticate(authToken);
			return auth;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse resp, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		UserSS user = (UserSS) auth.getPrincipal();
		String token = null;
//		if (user.hasRole(UserRole.SELLER)) {
//			token = jwtUtil.generateNonExpiringToken(user.getUsername());
//		} else {
			token = jwtUtil.generateToken(user.getUsername());
//		}
		
		resp.addHeader("Authorization", "Bearer " + token);
		resp.addHeader("Profile", user.getAuthorities().toString());
		resp.addHeader("access-control-expose-headers", "Authorization");
		resp.addHeader("access-control-expose-headers", "Profile");
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			response.getWriter().append(json(exception.getMessage()));
		}

		private String json(String msg) {
			String error = "Nome de usuário ou senha inválidos";
			if (msg != null && !msg.isEmpty()) {
				if (msg.equals("Bad credentials")) {
					msg = "Login ou senha incorretos";
				}
				error = msg;
			}
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Erro de autenticação\", "
					+ "\"message\": \"" + error + "\", " + "\"path\": \"/login\"}";
		}
	}
}
