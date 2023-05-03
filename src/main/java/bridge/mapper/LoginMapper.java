package bridge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import bridge.dto.LoginDto;
import bridge.dto.UserDto;
import bridge.dto.UsersDto;

@Mapper
public interface LoginMapper {
	public UserDto login(LoginDto loginDto) throws Exception;
	public int registUser(UserDto userDto) throws Exception;
	public UserDto selectUserByUserId(String userId) ;
	public UserDto getloginDto(UserDto userDto);
	//
	UsersDto passInformation(UsersDto usersDto) throws Exception;
	
	List<UsersDto> selectUserId(UsersDto usersDto) throws Exception;
	int userIdCheck(String userId) throws Exception;
}
