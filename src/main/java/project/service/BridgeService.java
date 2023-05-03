package project.service;

import java.util.List;

import project.dto.PartnerContentDto;
import project.dto.PartnerDetailCommentDto;
import project.dto.PartnerDetailDto;
import project.dto.PayListDto;

public interface BridgeService {

	// 1. 파트너 협업창 조회
	
	// 2. 파트너 협업창 게시글 조회
	public List<PartnerContentDto> selectPartnerContent(int pdIdx) throws Exception;
	
	// 3. 파트너 협업창 게시글 상세조회
	public PartnerContentDto selectPartnerContentDetail(int pcIdx) throws Exception;
		
	// 4. 파트너 협업창 게시글 등록
	public int insertPartnerContent(PartnerContentDto partnerContentDto) throws Exception;
	
	// 5. 파트너 협업창 게시글 수정
	public int updatePartnerContent(PartnerContentDto partnerContentDto) throws Exception;
	
	// 6. 파트너 협업창 게시글 삭제
	public int deletePartnerContent(int pcIdx) throws Exception;
	
	// 7. 파트너 협업창 결제 내역
	public PayListDto selectPayList(PayListDto payListDto) throws Exception;
	
	// 8. 파트너 협업창 작업목록 조회
	public List<PartnerDetailDto> selectProjectList(String userId1) throws Exception;
	
	// 9. 파트너 협업창 게시글의 댓글 조회
	public List<PartnerDetailCommentDto> selectPartnerComment(int pcIdx) throws Exception;
	
	// 10. 파트너 협업창 게시글의 댓글 작성
	public int insertPartnerComment(PartnerDetailCommentDto partnerDetailCommentDto) throws Exception;
}
