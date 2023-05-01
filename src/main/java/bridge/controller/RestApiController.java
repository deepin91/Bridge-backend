package bridge.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import bridge.dto.AnnouncementDto;
import bridge.dto.ApproveResponseDto;
import bridge.dto.CommentsDto;
import bridge.dto.MusicDto;
import bridge.dto.ReadyResponseDto;
import bridge.dto.ReportDto;
import bridge.dto.UserDto;
import bridge.service.BridgeService;
import bridge.service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
// @SessionAttributes("tid") 
public class RestApiController {

	@Autowired
	private BridgeService bridgeService;

	// 잼 음원 등록
	@PostMapping("/api/insertmusic/{cIdx}")
	public ResponseEntity<Map<String, Object>> insertMusic(@PathVariable("cIdx") int cIdx,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		String UPLOAD_PATH = "C:\\Temp\\";
		int insertedCount = 0;
		String uuid = UUID.randomUUID().toString();
		List<String> fileNames = new ArrayList<>();

		Map<String, Object> result = new HashMap<>();

		try {
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
				insertedCount++;

				MusicDto musicDto = new MusicDto();
				musicDto.setCIdx(cIdx);
				musicDto.setMusicTitle(originFileName);
				musicDto.setMusicUUID(uuid);
				bridgeService.insertMusic(musicDto);
			}

			if (insertedCount > 0) {
				result.put("uuid", uuid);
				result.put("fileNames", fileNames);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				result.put("message", "No files uploaded");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", "파일 업로드 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@GetMapping("/api/getMusic/{musicUUID}")
	public void getMusic(@PathVariable("musicUUID") String musicUUID, HttpServletResponse response) throws Exception {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String UPLOAD_PATH = "C:\\Temp\\";
		System.out.println(">>>>>>>>>>>>>>>>>>>>    " + musicUUID);
		System.out.println("++++++++++++++++++++++" + response);
		try {
			response.setHeader("Content-Disposition", "inline;");
			byte[] buf = new byte[1024];
			fis = new FileInputStream(UPLOAD_PATH + musicUUID + ".mp3");
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(buf, 0, 1024)) != -1) {
				bos.write(buf, 0, read);
			}
		} finally {
			bos.close();
			bis.close();
			fis.close();
		}
	}

	// 추가
	// 음원 분리하는 컨테이너 실행
	@GetMapping("/api/docker/{musicUUID}")
	public ResponseEntity<Map<String, Object>> dockerList(@PathVariable("musicUUID") String musicUUID)
			throws Exception {

		String musicUuid = musicUUID + ".mp3";

		final String command = "docker container run -d --rm -w /my-app -v  c:\\test:/my-app sihyun2/spleeter  /bin/bash -c \"spleeter separate -p spleeter:5stems -o output \""
				+ musicUuid;

		Process process = null;
		Map<String, Object> result = new HashMap<>();
		List<String> uuids = new ArrayList<>();
		result.put("uuids", uuids);
		try {
			process = Runtime.getRuntime().exec(command);
			process.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);
	}

	// 컨테이너 실행 여부 조회
	@GetMapping("/api/IsDockerRun")
	public ResponseEntity<Boolean> isDockerRun() {
		final String command = "docker container ls";
		boolean isRunning = false;
		try {
			Process process = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			List<String> list = reader.lines().toList();

			Iterator<String> iterator = list.iterator();
			while (iterator.hasNext()) {
				String line = iterator.next();
				if (line.contains("sihyun2/spleeter")) {
					isRunning = true;
					break;
				}
			}
			process.waitFor();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(isRunning);
		return ResponseEntity.ok(isRunning);
	}

	// 음원 분리된 폴더 파일 불러오기
	@GetMapping("/api/splitedMusic/{musicUUID}")
	public List<String> splitedMusic(@PathVariable("musicUUID") String musicUUID) throws Exception {
		String path = "C:\\test\\output\\" + musicUUID + "\\";
		File file = new File(path);

		File[] files = file.listFiles();
		List<String> fileNames = new ArrayList<>();

		for (File f : files) {
			String fileName = f.getName();
			fileNames.add(fileName);
			System.out.println("=================" + fileNames);
		}
		return fileNames;
	};

	// 분리된 음원 재생
	@GetMapping("/api/getSplitedMusic/{musicUUID}/{fn}")
	public void getSplitedMusic(@PathVariable("musicUUID") String musicUUID, HttpServletResponse response,
			@PathVariable("fn") String fn) throws Exception {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		String path = "C:\\test\\output\\" + musicUUID + "\\" + fn;
		System.out.println(">>>>>>>>>>>>>>>>>>>>    " + musicUUID);
		System.out.println("111111111111111" + fn);
		System.out.println("++++++++++++++++++++++" + response);
		try {
			response.setHeader("Content-Disposition", "inline;");
			byte[] buf = new byte[1024];
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			bos = new BufferedOutputStream(response.getOutputStream());
			int read;
			while ((read = bis.read(buf, 0, 1024)) != -1) {
				bos.write(buf, 0, read);
			}
		} finally {

		}
	}

	// 분리된 음악 파일 다운로드
	@GetMapping("/api/downloadSplitedMusic/{musicUUID}/{fileName:.+}")
	public void downloadSplitedMusic(@PathVariable("musicUUID") String musicUUID,
			@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception {
		String filePath = "C:\\test\\output\\" + musicUUID + "\\" + fileName;
		File file = new File(filePath);
		if (file.exists()) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			try (FileInputStream inputStream = new FileInputStream(file);
					ServletOutputStream outputStream = response.getOutputStream()) {
				byte[] buffer = new byte[1024];
				int length;
				while ((length = inputStream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, length);
				}
				outputStream.flush();
			}
		} else {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	// 음원 분리 음악 파일 insert
	@PostMapping("/api/insertMusicForSplit/{cIdx}")
	public ResponseEntity<Map<String, Object>> insertMusicForSplit(@PathVariable("cIdx") int cIdx,
			@RequestPart(value = "files", required = false) MultipartFile[] files) throws Exception {
		String UPLOAD_PATH = "C:\\test\\";
		int insertedCount = 0;
		String uuid = UUID.randomUUID().toString();
		List<String> fileNames = new ArrayList<>();

		Map<String, Object> result = new HashMap<>();

		try {
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
				insertedCount++;

				MusicDto musicDto = new MusicDto();
				musicDto.setMusicTitle(originFileName);
				musicDto.setMusicUUID(uuid);
				musicDto.setCIdx(cIdx);
				bridgeService.insertMusic(musicDto);
			}

			if (insertedCount > 0) {
				result.put("uuid", uuid);
				result.put("fileNames", fileNames);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				result.put("message", "No files uploaded");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("message", "파일 업로드 중 오류가 발생했습니다.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	// 잼 코멘트
	@PostMapping("/api/insertComments/{cIdx}")
	public ResponseEntity<Map<String, Object>> insertComments(@RequestBody CommentsDto commentsDto,
			@PathVariable("cIdx") int cIdx) throws Exception {
		int insertedCount = 0;
		try {
			commentsDto.setCIdx(cIdx);
			commentsDto.getUserId();

			insertedCount = bridgeService.insertComments(commentsDto);
			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("ccIdx", commentsDto.getCcIdx());
//				result.put("cIdx", commentsDto.getCIdx());

				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<>();
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	@GetMapping("/api/openComments/{cIdx}")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> openComments(@PathVariable("cIdx") int cIdx) throws Exception {
		List<CommentsDto> list = bridgeService.selectCommentsList(cIdx);
		Map<String, Object> result = new HashMap<>();
		result.put("selectCommentsList", list);
		if (result != null && result.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PutMapping("/api/Comments/{ccIdx}")
	public ResponseEntity<Object> updateComments(@PathVariable("ccIdx") int ccIdx, @RequestBody CommentsDto commentsDto)
			throws Exception {
		try {
			commentsDto.setCcIdx(ccIdx);
			bridgeService.updateComments(commentsDto);
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}

	@DeleteMapping("/api/CommentsDelete/{ccIdx}")
	public ResponseEntity<Object> deleteComments(@PathVariable("ccIdx") int ccIdx) throws Exception {
		try {
			bridgeService.deleteComments(ccIdx);
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
		}
	}

	// 신고 작성
	@PostMapping("/api/report/{reportedUserId}")
	public ResponseEntity<Map<String, Object>> insertReport(@RequestBody ReportDto reportDto,
			@PathVariable("reportedUserId") String reportedUserId) throws Exception {
		int insertedCount = 0;
		try {
			reportDto.setReportedUserId(reportedUserId);
			reportDto.getUserId();

			insertedCount = bridgeService.insertReport(reportDto);
			if (insertedCount > 0) {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "정상적으로 등록되었습니다.");
				result.put("reportedUserId", reportDto.getReportedUserId());
				result.put("userId", reportDto.getUserId());

				return ResponseEntity.status(HttpStatus.OK).body(result);
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("message", "등록된 내용이 없습니다.");
				result.put("count", insertedCount);
				return ResponseEntity.status(HttpStatus.OK).body(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Map<String, Object> result = new HashMap<>();
			result.put("message", "등록 중 오류가 발생했습니다.");
			result.put("count", insertedCount);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
		}
	}

	// 공지 전부 불러오기
	@GetMapping("/api/announcementList")
	public ResponseEntity<List<AnnouncementDto>> AnnouncementList() throws Exception {
		List<AnnouncementDto> list = bridgeService.announcementList();
		if (list == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(list);
		}
	}

	// 특정 공지 불러오기
	@GetMapping("/api/announcementDetail/{aIdx}")
	public ResponseEntity<AnnouncementDto> announcementDetail(@PathVariable("aIdx") int aIdx) throws Exception {
		AnnouncementDto announcementDto = bridgeService.announcementDetail(aIdx);
		if (announcementDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(announcementDto);
		}
	}

	// 유저 포인트 충전
	@GetMapping("/api/chargePoint/{userId}")
	public ResponseEntity<UserDto> chargePonint(@PathVariable("userId") String userId) throws Exception {
		UserDto userDto = bridgeService.chargePoint(userId);
		if (userDto == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(userDto);
		}
	}

//	@PostMapping("/api/doCharge/{userId}")
//	public ResponseEntity<String> doCharge(@PathVariable("userId") String userId, @RequestBody UserDto userDto)
//			throws Exception {
//		try {
//			userDto.setUserId(userId);
////			int updatedCount = bridgeService.doCharge(userDto);
//			if (updatedCount != 1) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("충전에 실패했습니다");
//			} else {
//				return ResponseEntity.status(HttpStatus.OK).body("포인트가 충전되었습니다");
//			}
//		} catch (Exception e) {
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("포인트 충전 오류 발생");
//		}
//	}



}
