package bridge.service;

import java.util.List;

import bridge.dto.CommentsDto;
import bridge.dto.MusicDto;

public interface BridgeService {

	public void insertMusic(MusicDto musicDto);

	public String selectMusic(String musicUUID) throws Exception;

	public int insertComments(CommentsDto commentsDto);

	public List<CommentsDto> selectCommentsList(int cIdx);

	public void updateComments(CommentsDto commentsDto);

	public void deleteComments(int ccIdx);

}
