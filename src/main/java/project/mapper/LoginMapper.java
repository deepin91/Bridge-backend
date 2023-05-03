package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.LoginDto;
import project.dto.UsersDto;

@Mapper
public interface LoginMapper {
	UsersDto login(LoginDto loginDto) throws Exception;
	int registUser(UsersDto userDto) throws Exception;
	UsersDto selectUserByUserId(String userId);
	UsersDto passInformation(UsersDto usersDto) throws Exception;
	
	List<UsersDto> selectUserId(UsersDto usersDto) throws Exception;
	int userIdCheck(String userId) throws Exception;
	
	
}
