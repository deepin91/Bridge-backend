package bridge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import bridge.dto.AnnouncementDto;
import bridge.dto.CommentsDto;
import bridge.dto.KakaopayDto;
import bridge.dto.MusicDto;
import bridge.dto.ReportDto;
import bridge.dto.UserDto;

@Mapper
public interface BridgeMapper {

	void insertMusic(MusicDto musicDto);

	String selectMusic(String musicUUID);

	// 잼 코맨트
	int insertComments(CommentsDto commentsDto);

	List<CommentsDto> selectCommentsList(int cIdx);

	void updateComments(CommentsDto commentsDto);

	void deleteComments(int ccIdx);

	//신고 작성
	int insertReport(ReportDto reportDto);
	
	//공지 리스트
	List<AnnouncementDto> announcementList();
	//특정 공지
	AnnouncementDto announcementDetail(int aIdx);
	//포인트 충전
	UserDto chargePoint(String userId);

	int doCharge(UserDto userDto);


}
