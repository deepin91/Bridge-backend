package bridge.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bridge.dto.UserProfileDto;
import bridge.service.BridgeService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class UserProfileController {

	@Autowired
	BridgeService bridgeService;

	// 프로필 작성
	// 관리자 : 영화 정보 등록
	@PostMapping("/api/insertProfile/{userId}")
	public ResponseEntity<Map<String, Object>> insertmovie(@PathVariable("userId") String userId,
			@RequestPart(value = "data", required = false) UserProfileDto userProfileDto,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		String UPLOAD_PATH = "C:\\Temp\\";
		int insertedCount = 0;
		String fileNames = "";
		try {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename(); // 원본 파일 명
				long fileSize = mf.getSize(); // 파일 사이즈
				System.out.println("originFileName : " + originFileName);
				System.out.println("fileSize : " + fileSize);
				String safeFile = System.currentTimeMillis() + originFileName;
				userProfileDto.setProfileImg(originFileName);
				fileNames = fileNames + "," + safeFile;
				try {
					File f1 = new File(UPLOAD_PATH + originFileName);
					mf.transferTo(f1);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			insertedCount = bridgeService.insertProfile(userProfileDto, files);

			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);
				result.put("userId", userProfileDto.getUserId());
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			Map<String, Object> result = new HashMap<>();
			System.out.println(e);
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

}
