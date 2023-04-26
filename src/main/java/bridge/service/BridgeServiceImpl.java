package bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.dto.CommentsDto;
import bridge.dto.MusicDto;
import bridge.mapper.BridgeMapper;

@Service
public class BridgeServiceImpl implements BridgeService {

	@Autowired
	BridgeMapper bridgeMapper;
	
	@Override
	public void insertMusic(MusicDto musicDto) {
		bridgeMapper.insertMusic(musicDto);
	}

	@Override
	public int insertComments(CommentsDto commentsDto) {
		return bridgeMapper.insertComments(commentsDto);	
		
	}

	@Override
	public List<CommentsDto> selectCommentsList(int cIdx) {
		return bridgeMapper.selectCommentsList(cIdx);
	}

	@Override
	public void updateComments(CommentsDto commentsDto) {
		bridgeMapper.updateComments(commentsDto);
		
	}

	@Override
	public void deleteComments(int ccIdx) {
		bridgeMapper.deleteComments(ccIdx);
		
	}

	@Override
	public String selectMusic(String musicUUID) throws Exception {
		return bridgeMapper.selectMusic(musicUUID);
	}
}
