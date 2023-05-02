package bridge.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bridge.dto.LoginDto;
import bridge.dto.UserDto;
import bridge.security.JwtTokenUtil;
import bridge.service.LoginService;

@RestController
public class RestLoginApiController {
	@Autowired
	private LoginService loginService;
	
	@PostMapping("/api/regist")
	public ResponseEntity<Object> regist(@RequestBody UserDto userDto) throws Exception {
		int registedCount = loginService.registUser(userDto);
		if (registedCount > 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body(registedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(registedCount);
		}
		
	}
	@Autowired
	JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/api/login")
	public void login(@RequestBody UserDto userDto,HttpServletResponse response) throws Exception {
		UserDto userDtos = loginService.getloginDto(userDto);
		String jwtToken = jwtTokenUtil.generateToken(userDtos);
		response.setHeader("token", jwtToken);
		response.getWriter().write(jwtToken);
		
	}
	@GetMapping("/api/user")	
	public ResponseEntity<UserDto> currentUserName(Authentication authentication) {
		try {
			UserDto userDto = (UserDto) authentication.getPrincipal();
			return ResponseEntity.status(HttpStatus.OK).body(userDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
		}
	}

}
 