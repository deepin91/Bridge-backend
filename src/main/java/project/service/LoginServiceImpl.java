package project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import project.dto.KaKaoInformationDto;
import project.dto.LoginDto;
import project.dto.UsersDto;
import project.mapper.LoginMapper;

@Service 
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginMapper loginMapper;
	//해시값 설정
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	public UsersDto login(LoginDto loginDto) throws Exception {
		return loginMapper.login(loginDto);
	}
	
	@Override
	public int registUser(UsersDto userDto) throws Exception {
		// 패스워드를 암호화 해서 새로 저장
		userDto.setUserPw(passwordEncoder.encode(userDto.getUserPw()));
		return loginMapper.registUser(userDto);
	}
	
	//String username, String password, boolean enabled, 
	//boolean accountNonExpired, boolean credentialsNonExpired, 
	//boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsersDto userDto = loginMapper.selectUserByUserId(username);
		if(userDto == null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(userDto.getUserId(),userDto.getUserPw(),true,true,true,true,new ArrayList<>());
	}
	
	// 외부 로그인
	@Override
	public UsersDto passInformation(UsersDto usersDto) throws Exception {
		return loginMapper.passInformation(usersDto);
	}
	
	@Override
	public int userIdCheck(String userIdCheck) throws Exception {
		int result = loginMapper.userIdCheck(userIdCheck);
		return result;
	}
}
	
	

