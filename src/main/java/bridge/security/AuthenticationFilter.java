package bridge.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import bridge.dto.LoginDto;
import bridge.dto.UserDto;
import bridge.mapper.LoginMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private LoginMapper loginMapper; // 회원정보 조회 시 사용
	private Environment env;	// 토큰 생성 시 사용
	private JwtTokenUtil jwtTokenUtil;

	public AuthenticationFilter(  LoginMapper loginMapper,Environment env,JwtTokenUtil jwtTokenUtil) {
		this.loginMapper=loginMapper;
		this.env=env;
		this.jwtTokenUtil=jwtTokenUtil;
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginDto creds = new ObjectMapper().readValue(request.getInputStream(),LoginDto.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getUserId(),
							creds.getUserPassword(),
							new ArrayList<>()
							)
					);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String username= (((User)authResult.getPrincipal()).getUsername());
		UserDto userDto = loginMapper.selectUserByUserId(username);
		
//		//설정 파일에서 비밀키를 가져와서 서명에 사용할 키를 생성
//		//BASE64로 인코딩된 문자열
//		String secret = env.getProperty("token.secret");
//		Key hmackey = new SecretKeySpec(
//				Base64.getDecoder().decode(secret),SignatureAlgorithm.HS256.getJcaName()
//				);
//		
//		Instant now = Instant.now();
//		Long expirationTime = Long.parseLong(env.getProperty("token.expiration-time"));
//		String jwtToken = Jwts.builder()
//				.claim("name", userDto.getUserName())
//				.claim("email", userDto.getUserEmail())
//				.setSubject(userDto.getUserId())
//				.setId(UUID.randomUUID().toString())
//				.setIssuedAt(Date.from(now))
//				.setExpiration(Date.from(now.plus(expirationTime, ChronoUnit.MILLIS)))
//				.signWith(hmackey)
//				.compact();
//		log.debug(jwtToken);
		String jwtToken = jwtTokenUtil.generateToken(userDto);
		response.setHeader("token", jwtToken);
		response.getWriter().write(jwtToken);
	}

}
