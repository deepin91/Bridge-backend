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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bridge.dto.MusicDto;
import bridge.dto.ReportDto;
import bridge.dto.UserDto;
import bridge.mapper.BridgeMapper;
import bridge.service.BridgeService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class AdminController {

	@Autowired
	private BridgeService bridgeService;
	
	@Autowired
	private BridgeMapper bridgeMapper;

	// 신고 전부 불러오기
	@GetMapping("/api/openReportList")
	public ResponseEntity<List<ReportDto>> openReportList() throws Exception {
		List<ReportDto> list = bridgeService.openReportList();
		if (list == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}

	// 특정 신고 불러오기
	@GetMapping("/api/openReportDetail/{reportIdx}")
	public ResponseEntity<ReportDto> openReportDetail(@PathVariable("reportIdx") int reportIdx) throws Exception {
		ReportDto reportDto = bridgeService.openReportDetail(reportIdx);
		if (reportDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(reportDto);
		}
	}

	@GetMapping("/api/reportCount/{userId}")
	public ResponseEntity<Object> selectReportCount(@PathVariable("userId") String userId)
			throws Exception {
		System.out.println(">>>>>>>>>>>>>>>>>>" + bridgeMapper.selectReportCount(userId));
		try {
			int a = bridgeMapper.selectReportCount(userId);
			return ResponseEntity.status(HttpStatus.OK).body(a);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}

	// 신고 처리
	@PutMapping("/api/handleReport/{userId}")
	public ResponseEntity<Object> handleReport(@PathVariable("userId") String userId, @RequestBody UserDto userDto)
			throws Exception {
		try {
			userDto.setUserId(userId);
			bridgeService.handleReport(userDto);
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}
	
}
