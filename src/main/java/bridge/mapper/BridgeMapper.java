package bridge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import bridge.dto.CommentsDto;
import bridge.dto.MusicDto;

@Mapper
public interface BridgeMapper {

	void insertMusic(MusicDto musicDto);

	String selectMusic(String musicUUID);

	// 잼 코맨트
	int insertComments(CommentsDto commentsDto);

	List<CommentsDto> selectCommentsList(int cIdx);

	void updateComments(CommentsDto commentsDto);

	void deleteComments(int ccIdx);

}
