package project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import project.dto.PartnerContentDto;
import project.dto.PartnerDetailCommentDto;
import project.dto.PartnerDetailDto;
import project.dto.PayListDto;
import project.mapper.BridgeMapper;

@Slf4j
@Service
public class BridgeServiceImpl implements BridgeService {

	@Autowired
	private BridgeMapper bridgeMapper;

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

}
