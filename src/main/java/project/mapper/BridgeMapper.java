package project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import project.dto.PartnerContentDto;
import project.dto.PartnerDetailCommentDto;
import project.dto.PartnerDetailDto;
import project.dto.PayListDto;
import project.dto.UsersDto;

@Mapper
public interface BridgeMapper {
	
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

	

}
