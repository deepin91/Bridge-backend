package project.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import project.dto.KaKaoInformationDto;
import project.dto.LoginDto;
import project.dto.UsersDto;

public interface LoginService extends UserDetailsService {
	public UsersDto login(LoginDto loginDto) throws Exception;
	public int registUser(UsersDto userDto) throws Exception;
	// 외부 로그인
	public UsersDto passInformation(UsersDto usersDto) throws Exception;
	
//	public List<UserDto> selectUserId(UserDto userDto) throws Exception;
	public int userIdCheck(String userId) throws Exception;
}
