package bridge.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import bridge.dto.LoginDto;
import bridge.dto.UserDto;
import bridge.dto.UsersDto;

public interface LoginService extends UserDetailsService {
	public UserDto login(LoginDto loginDto) throws Exception;

	public int registUser(UserDto userDto) throws Exception;

	public UserDto getloginDto(UserDto userDto);

	// 외부 로그인
	public UsersDto passInformation(UsersDto usersDto) throws Exception;
	//ID중복체크
	public int userIdCheck(String userId) throws Exception;
}
