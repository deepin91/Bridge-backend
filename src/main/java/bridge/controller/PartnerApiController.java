package bridge.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bridge.dto.ComposerRequestDto;
import bridge.dto.ComposerRequestTagDto;
import bridge.mapper.BridgeMapper;
import bridge.service.BridgeService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
public class PartnerApiController {
	@Autowired
	BridgeService bridgeService;
	
	@Autowired
	BridgeMapper bridgeMapper;

	// 파트너 모집글 작성
	@PostMapping("/api/insertPartnerWrite")
	public ResponseEntity<Map<String, Object>> insertPartnerWrite(
			@RequestPart(value = "data", required = false) ComposerRequestDto composerRequestDto,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		String UPLOAD_PATH = "C:\\Temp\\";
		int insertedCount = 0;
		
		String fileNames = "";
		try {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename(); // 원본 파일 명
				long fileSize = mf.getSize(); // 파일 사이즈
				String saveFile = System.currentTimeMillis() + originFileName;
				composerRequestDto.setCrPhoto(originFileName); //원본 파일명으로 crPhoto set
				fileNames = fileNames + "," + saveFile;
				try {
					File f1 = new File(UPLOAD_PATH + originFileName);
					mf.transferTo(f1);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}
			
			insertedCount = bridgeService.insertPartnerWrite(composerRequestDto, files); //서비스 실행

			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);
				result.put("userId", composerRequestDto.getUserId());
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
	
//	@PostMapping("/api/test/{crIdx}")
//	public  ResponseEntity<Object> test(@PathVariable("crIdx") int crIdx, @RequestBody ComposerRequestTagDto composerRequestTagDto) throws Exception {
//		int a = bridgeMapper.insertTest(crIdx);
////		String b = composerRequestTagDto.setCrtTag(composerRequestTagDto.getCrtTag());
//		
//		if (a > 0) {
//			composerRequestTagDto.setCrtTag(composerRequestTagDto.getCrtTag());
//			System.out.println(">>>>>>>>>>>" + Arrays.toString(composerRequestTagDto.getCrtTag()));
//			return ResponseEntity.status(HttpStatus.CREATED).body(composerRequestTagDto);
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body("2");
//		}
//	}

}
