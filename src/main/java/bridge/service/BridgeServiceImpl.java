package bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.dto.AnnouncementDto;
import bridge.dto.CommentsDto;
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
import bridge.mapper.BridgeMapper;
import bridge.mapper.LoginMapper;

@Service
public class BridgeServiceImpl implements BridgeService {

	@Autowired
	BridgeMapper bridgeMapper;
	@Autowired
	LoginMapper loginMapper;
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

	@Override
	public int insertReport(ReportDto reportDto) {
		return bridgeMapper.insertReport(reportDto);
	}

	@Override
	public List<AnnouncementDto> announcementList() {
		return bridgeMapper.announcementList();
	}

	@Override
	public AnnouncementDto announcementDetail(int aIdx) {
		return bridgeMapper.announcementDetail(aIdx);
	}

	@Override
	public UserDto chargePoint(String userId) {
		return bridgeMapper.chargePoint(userId);
	}

	@Override
	public List<ReportDto> openReportList() {
		return bridgeMapper.openReportList();
	}

	@Override
	public ReportDto openReportDetail(int reportIdx) {
		return bridgeMapper.openReportDetail(reportIdx);
	}

	@Override
	public void handleReport(UserDto userDto) {
		bridgeMapper.handleReport(userDto);
	}

	@Override
	public int insertProfile(UserProfileDto userProfileDto) {
		return bridgeMapper.insertProfile(userProfileDto);
	}

//	@Override
//	public int doCharge(UserDto userDto) {
//		return bridgeMapper.doCharge(userDto);
//	}

	// 1. 파트너 협업창 조회

	// 2. 파트너 협업창 게시글 조회
	@Override
	public List<PartnerContentDto> selectPartnerContent(int pdIdx) throws Exception {
		return bridgeMapper.selectPartnerContent(pdIdx);
	}

	// 3. 파트너 협업창 게시글 상세조회
	@Override
	public PartnerContentDto selectPartnerContentDetail(int pcIdx) throws Exception {
		return bridgeMapper.selectPartnerContentDetail(pcIdx);
	}

	// 4. 파트너 협업창 게시글 등록
	@Override
	public int insertPartnerContent(PartnerContentDto partnerContentDto) throws Exception {
		return bridgeMapper.insertPartnerContent(partnerContentDto);
	}

	// 5. 파트너 협업창 게시글 수정
	@Override
	public int updatePartnerContent(PartnerContentDto partnerContentDto) throws Exception {
		return bridgeMapper.updatePartnerContent(partnerContentDto);
	}

	// 6. 파트너 협업창 게시글 삭제
	@Override
	public int deletePartnerContent(int pcIdx) throws Exception {
		return bridgeMapper.deletePartnerContent(pcIdx);
	}

	// 7. 파트너 협업창 결제 내역
	@Override
	public PayListDto selectPayList(PayListDto payListDto) throws Exception {
		return bridgeMapper.selectPayList(payListDto);
	}

	// 8. 파트너 협업창 작업목록 조회
	@Override
	public List<PartnerDetailDto> selectProjectList(String userId1) throws Exception {
		return bridgeMapper.selectProjectList(userId1);
	}

	// 9. 파트너 협업창 게시글의 댓글 조회
	@Override
	public List<PartnerDetailCommentDto> selectPartnerComment(int pcIdx) throws Exception {
		return bridgeMapper.selectPartnerComment(pcIdx);
	}

	// 10. 파트너 협업창 게시글의 댓글 작성
	@Override
	public int insertPartnerComment(PartnerDetailCommentDto partnerDetailCommentDto) throws Exception {
		return bridgeMapper.insertPartnerComment(partnerDetailCommentDto);
	}

	@Override
	public void insertTag(TagDto tag) {
		// TODO Auto-generated method stub
		bridgeMapper.insertTag(tag);
	}

	@Override
	public List<UserProfileDto> getPorfile(String userId) {
		// TODO Auto-generated method stub
		return bridgeMapper.getPorfile(userId);
	}

	@Override
	public List<TagDto> getTaglist(String userId) {
		// TODO Auto-generated method stub
		return bridgeMapper.getTaglist(userId);
	}

	@Override
	public List<ReviewDto> getReview(String userId) {
		// TODO Auto-generated method stub
		return bridgeMapper.getReview(userId);
	}

	@Override
	public UserDto getUserDto(String userId) {
		// TODO Auto-generated method stub
		return loginMapper.selectUserByUserId(userId);
	}

}
