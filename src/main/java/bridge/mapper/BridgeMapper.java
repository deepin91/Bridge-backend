package bridge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import bridge.dto.AnnouncementDto;
import bridge.dto.ApproveResponseDto;
import bridge.dto.CommentsDto;
import bridge.dto.KakaopayDto;
import bridge.dto.MusicDto;
import bridge.dto.PartnerContentDto;
import bridge.dto.PartnerDetailCommentDto;
import bridge.dto.PartnerDetailDto;
import bridge.dto.PayListDto;
import bridge.dto.ReportDto;
import bridge.dto.ReviewDto;
import bridge.dto.TagDto;
import bridge.dto.UserDto;
import bridge.dto.UserProfileDto;

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
	void plusReportCount(String reportedUserId);
	//신고 리스트
	List<ReportDto> openReportList();
	
	//공지 리스트
	List<AnnouncementDto> announcementList();
	//특정 공지
	AnnouncementDto announcementDetail(int aIdx);
	//포인트 충전
	UserDto chargePoint(String userId);

	int doCharge(ApproveResponseDto approveResponse);

	ReportDto openReportDetail(int reportIdx);

	void handleReport(UserDto userDto);

	int selectReportCount(String userId);

	int insertProfile(UserProfileDto userProfileDto);

	// 1. 파트너 협업창 조회
	
	
	// 2. 파트너 협업창 게시글 조회
	List<PartnerContentDto> selectPartnerContent(int pdIdx) throws Exception;
	
	// 3. 파트너 협업창 게시글 상세조회
	PartnerContentDto selectPartnerContentDetail(int pcIdx) throws Exception;
	
	// 4. 파트너 협업창 게시글 등록
	int insertPartnerContent(PartnerContentDto partnerContentDto) throws Exception;
	
	// 5. 파트너 협업창 게시글 수정
	int updatePartnerContent(PartnerContentDto partnerContentDto) throws Exception;
	
	// 6. 파트너 협업창 게시글 삭제
	int deletePartnerContent(int pcIdx) throws Exception;
	
	// 7. 파트너 협업창 결제 내역
	PayListDto selectPayList(PayListDto payListDto) throws Exception;
	
	// 8. 파트너 협업창 작업목록 조회
	List<PartnerDetailDto> selectProjectList(String userId1) throws Exception;
	
	// 9. 파트너 협업창 게시글의 댓글 조회
	List<PartnerDetailCommentDto> selectPartnerComment(int pcIdx) throws Exception;
	
	// 10. 파트너 협업창 게시글의 댓글 작성
	int insertPartnerComment(PartnerDetailCommentDto partnerDetailCommentDto) throws Exception;

	// 11, 파트너 협업창 게시글의 댓글 삭제
	int deletePartnerComment(int pdcIdx) throws Exception;
	
	// 12. 파트너 협업창 작업 진행 상황
	int partnerComplete(int pdIdx) throws Exception;
	
	void insertTag(TagDto tag);

	List<UserProfileDto> getPorfile(String userId);

	List<TagDto> getTaglist(String userId);

	List<ReviewDto> getReview(String userId);


}
