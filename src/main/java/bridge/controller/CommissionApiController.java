package bridge.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bridge.dto.CommissionCommentDto;
import bridge.dto.CommissionDetailDto;
import bridge.dto.CommissionDto;
import bridge.dto.MusicDto;
import bridge.dto.TipDto;
import bridge.service.CommissionService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class CommissionApiController {

	@Autowired
	CommissionService commissionService;

	// 작업 목록 전부 불러오기
	@GetMapping("/api/getCommissionList/{userId}")
	public ResponseEntity<List<CommissionDto>> getCommissionList(@PathVariable("userId") String userId)
			throws Exception {
		List<CommissionDto> list = commissionService.getCommissionList(userId);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 작업진행상황, 예치금 불러오기
	@GetMapping("/api/getProgress/{cIdx}")
	public ResponseEntity<List<CommissionDto>> getProgress(@PathVariable("cIdx") int cIdx) throws Exception {
		List<CommissionDto> list = commissionService.getProgress(cIdx);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	// 작업 디테일 불러오기
	@GetMapping("/api/getCommissionDetail/{cIdx}")
	public ResponseEntity<List<CommissionDto>> getCommissionDetail(@PathVariable("cIdx") int cIdx) throws Exception {
		List<CommissionDto> list = commissionService.getCommissionDetail(cIdx);
		log.info("===============" + cIdx);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

	@PostMapping("/api/insertCommissionDetail/{cIdx}")
	public ResponseEntity<Map<String, Object>> insertCommissionDetail(@PathVariable("cIdx") int cIdx,
			@RequestPart(value = "data", required = false) CommissionDetailDto commissionDetail,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		String UPLOAD_PATH = "C:\\Temp\\";
		int insertedCount = 0;
		List<String> fileNames = new ArrayList<>();

		Map<String, Object> result = new HashMap<>();

		try {
			if (files != null && files.length > 0) {
				String uuid = UUID.randomUUID().toString();
				for (MultipartFile mf : files) {
					String originFileName = mf.getOriginalFilename();
					try {
						File f = new File(UPLOAD_PATH + File.separator + uuid + ".mp3");
						mf.transferTo(f);

					} catch (IllegalStateException e) {
						e.printStackTrace();
					}
					fileNames.add(originFileName);
					insertedCount++;
				}
				commissionDetail.setCdFile(uuid);
			}

			commissionDetail.setCIdx(cIdx);
			commissionService.insertCommissionDetail(commissionDetail);

			if (insertedCount > 0) {
				result.put("fileNames", fileNames);
			}

			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", "파일 업로드 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@PutMapping("/api/editCommissionDetail/{cidx}/{cdIdx}")
	public ResponseEntity<Object> editCommissionDetail(@PathVariable("cidx") int cidx, @PathVariable("cdIdx") int cdIdx,
			@RequestPart(value = "data", required = false) CommissionDetailDto commissionDetail,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {

		String UPLOAD_PATH = "C:\\Temp\\";
		String uuid = UUID.randomUUID().toString();
		List<String> fileNames = new ArrayList<>();

		try {
			if (files != null && files.length > 0) {
				for (MultipartFile mf : files) {
					String originFileName = mf.getOriginalFilename();
					try {
						File f = new File(UPLOAD_PATH + File.separator + uuid + ".mp3");
						System.out.println("---------------------------" + f);
						mf.transferTo(f);

					} catch (IllegalStateException e) {
						e.printStackTrace();
					}
					fileNames.add(originFileName);
				}
			}

			commissionDetail.setCIdx(cidx);
			commissionDetail.setCdIdx(cdIdx);

			if (fileNames.size() > 0) {
				commissionDetail.setCdFile(uuid);
			}

			System.out.println(">>>>>>>>>>>>>>>>>>>commissionDetail: " + commissionDetail);

			commissionService.editCommissionDetail(commissionDetail);

			return ResponseEntity.status(HttpStatus.OK).body(commissionDetail);
		} catch (Exception e) {
			e.printStackTrace();
			log.info(">>>>>>>>>>>>>>>>>>>" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		}
	}

	// 게시글 삭제
	@PutMapping("/api/delCommissionDetail/{cdIdx}")
	public ResponseEntity<Object> delCommissionDetail(@PathVariable("cdIdx") int cdIdx) throws Exception {
		commissionService.delCommissionDetail(cdIdx);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	// 게시 파일 삭제
	@PutMapping("/api/delCommissionFile/{cdIdx}")
	public ResponseEntity<Object> delCommissionFile(@PathVariable("cdIdx") int cdIdx) throws Exception {
		commissionService.delCommissionFile(cdIdx);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	// 작업목록에서 삭제
	@PutMapping("/api/delCommissionList/{cIdx}")
	public ResponseEntity<Object> delCommissionList(@PathVariable("cIdx") int cIdx) throws Exception {
		commissionService.delCommissionList(cIdx);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	// 작업완료
	@PutMapping("/api/commissionEnd/{cIdx}")
	public ResponseEntity<Object> commissionEnd(@PathVariable("cIdx") int cIdx) throws Exception {
		commissionService.commissionEnd(cIdx);
		commissionService.moneyToUser2(cIdx);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	// 작업목록에 생성
	@PostMapping("/api/insertCommission/{userId2}")
	public ResponseEntity<Object> insertCommission(@PathVariable("userId2") String userId2,
			@RequestBody CommissionDto commissionDto) throws Exception {
		commissionDto.setUserId2(userId2);
		commissionService.insertCommission(commissionDto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	// 댓글 작성
	@PostMapping("/api/insert/CommissionComment/{cdIdx}")
	public ResponseEntity<Object> CommissionComment(@PathVariable("cdIdx") int cdIdx,
			@RequestBody CommissionCommentDto commissionCommentDto) throws Exception {
		commissionCommentDto.setCdIdx(cdIdx);
		int insertCount = commissionService.CommissionComment(commissionCommentDto);
		if (insertCount > 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body(insertCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(insertCount);
		}
	}

	// 댓글 조회
	@GetMapping("/api/get/CommissionComment/{cdIdx}")
	public ResponseEntity<List<CommissionCommentDto>> CommissionComment(@PathVariable("cdIdx") int cdIdx) throws Exception {
		List<CommissionCommentDto> list = commissionService.CommissionComment(cdIdx);
		return ResponseEntity.status(HttpStatus.OK).body(list);
	}

}
