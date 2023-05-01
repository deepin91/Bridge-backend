package bridge.service;

import java.util.List;

import bridge.dto.AnnouncementDto;
import bridge.dto.CommentsDto;
import bridge.dto.MusicDto;
import bridge.dto.ReportDto;
import bridge.dto.UserDto;

public interface BridgeService {

	public void insertMusic(MusicDto musicDto);

	public String selectMusic(String musicUUID) throws Exception;

	public int insertComments(CommentsDto commentsDto);

	public List<CommentsDto> selectCommentsList(int cIdx);

	public void updateComments(CommentsDto commentsDto);

	public void deleteComments(int ccIdx);

	public int insertReport(ReportDto reportDto);

	//공지 
	public List<AnnouncementDto> announcementList();

	public AnnouncementDto announcementDetail(int aIdx);

	//포인트 충전
	public UserDto chargePoint(String userId);

	public int doCharge(UserDto userDto);


}
