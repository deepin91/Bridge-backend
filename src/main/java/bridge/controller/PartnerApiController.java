package bridge.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
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

		ComposerRequestTagDto dto = new ComposerRequestTagDto();
		dto.setCrIdx(composerRequestDto.getCrIdx());
		dto.setCrtTags(composerRequestDto.getCrtTag());

		String UPLOAD_PATH = "C:\\Temp\\";
		int insertedCount = 0;

		String fileNames = "";
		try {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename(); // 원본 파일 명
				long fileSize = mf.getSize(); // 파일 사이즈
				String saveFile = System.currentTimeMillis() + originFileName;
				composerRequestDto.setCrPhoto(originFileName); // 원본 파일명으로 crPhoto set
				fileNames = fileNames + "," + saveFile;
				try {
					File f1 = new File(UPLOAD_PATH + originFileName);
					mf.transferTo(f1);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
			}

			insertedCount = bridgeService.insertPartnerWrite(composerRequestDto, files); // 서비스 실행

			dto.setCrIdx(composerRequestDto.getCrIdx());
			bridgeService.insertCrtTag(dto);

			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("count", insertedCount);
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

	@GetMapping("/api/openPartnerList")
	public ResponseEntity<Map<String, Object>> openPartnerList() throws Exception {

		Map<String, Object> map = new HashMap<>();

		List<ComposerRequestDto> list = bridgeService.openPartnerList();
//		List<ComposerRequestTagDto> tagList = bridgeService.partnerTagList();

		map.put("partnerList", list);
//		map.put("partnerTag", tagList);

		if (list == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}
	};

	@GetMapping("/api/openTagList")
	public ResponseEntity<Map<String, Object>> openTagList() throws Exception {
		Map<String, Object> map = new HashMap<>();
		List<ComposerRequestTagDto> tagList = bridgeService.partnerTagList();
		map.put("partnerTag", tagList);

		if (tagList == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}
	}

	@GetMapping("/api/openPartnerDetail/{crIdx}")
	public ResponseEntity<Map<String, Object>> openPartnerDetail(@PathVariable("crIdx") int crIdx) throws Exception {
		Map<String, Object> map = new HashMap<>();
		ComposerRequestDto composerRequestDto = bridgeMapper.openPartnerDetail(crIdx);
		List<ComposerRequestTagDto> composerRequestTagDto = bridgeMapper.PartnerDetailTag(crIdx);
		map.put("partnerList", composerRequestDto);
		map.put("partnerTag", composerRequestTagDto);

		if (composerRequestDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(map);
		}

	}
	
	@GetMapping("/api/getImage/{imgName}")
	public void getImage(@PathVariable("imgName") String imgName, HttpServletResponse response) throws Exception {
	    FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream out = null;
	    String UPLOAD_PATH = "C:\\Temp\\";

	    try {
	        response.setContentType("image/jpeg");
	        fis = new FileInputStream(UPLOAD_PATH + imgName);
	        bis = new BufferedInputStream(fis);
	        out = response.getOutputStream();

	        byte[] buffer = new byte[1024];
	        while (bis.read(buffer) != -1) {
	            out.write(buffer);
	        }
	    } finally {
	        if (bis != null) {
	            bis.close();
	        }
	        if (out != null) {
	            out.close();
	        }
	        if (fis != null) {
	            fis.close();
	        }
	    }
	}
}
