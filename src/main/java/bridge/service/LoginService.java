package bridge.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import bridge.dto.LoginDto;
import bridge.dto.UserDto;

public interface LoginService extends UserDetailsService {
	public UserDto login(LoginDto loginDto) throws Exception;
	public int registUser(UserDto userDto) throws Exception;
	public UserDto getloginDto(UserDto userDto);
}
