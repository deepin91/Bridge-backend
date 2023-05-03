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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import bridge.dto.ConcertDto;
import bridge.dto.ConcertMusicDto;
import bridge.dto.UserDto;
import bridge.service.JamService;

@RestController
public class JamController {
	
	@Autowired
	JamService jamService;
	
//	@PostMapping("api/insertJam")
//	public ResponseEntity<Object> insertTip(@RequestBody ConcertDto concertDto, Authentication authentication)
//			throws Exception {
//		UserDto userDto = (UserDto) authentication.getPrincipal();
//		concertDto.setCWriter(userDto.getUserId());
//		int registedCount = jamService.insertJam(concertDto);
//		if (registedCount > 0) {
//			return ResponseEntity.status(HttpStatus.CREATED).body(registedCount);
//		} else {
//			return ResponseEntity.status(HttpStatus.OK).body(registedCount);
//		}
//		
//	}
	
	@PostMapping("/api/insertjam")
	public ResponseEntity<Integer> insertTip(
			@RequestPart(value = "data", required = false) ConcertDto concertDto,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			Authentication authentication) throws Exception {
		String UPLOAD_PATH = "C:\\Temp\\";
		String uuid = UUID.randomUUID().toString();
		List<String> fileNames = new ArrayList<>();
		Map<String, Object> result = new HashMap<>();
		int registedCount = 0;
		try {
			for (MultipartFile mf : files) {
				String originFileName = mf.getOriginalFilename();
				try {
					File f = new File(UPLOAD_PATH + File.separator + uuid + ".jpg");
					System.out.println("---------------------------" + f);
					mf.transferTo(f);

				} catch (IllegalStateException e) {
					e.printStackTrace();
				}
				fileNames.add(originFileName);
			
				UserDto userDto = (UserDto) authentication.getPrincipal();
				concertDto.setCWriter(userDto.getUserId());
				concertDto.setCPhoto(uuid);
				System.out.println("`111111111111111111111111111"+concertDto);
				System.out.println("`222222222222222222222222222"+userDto.getUserId());
				registedCount = jamService.insertJam(concertDto);
			}

			if (registedCount > 0) {
		
				return ResponseEntity.status(HttpStatus.OK).body(concertDto.getCIdx());
			} else {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	@PostMapping("/api/insertmusic/{cIdx}")
	public ResponseEntity<Integer> insertMusic(@PathVariable("cIdx") int cIdx,
			@RequestPart(value = "data", required = false) ConcertMusicDto concertMusicDto,
			@RequestPart(value = "files", required = false) MultipartFile[] files,
			Authentication authentication) throws Exception {
		String UPLOAD_PATH = "C:\\Temp\\";
		String uuid = UUID.randomUUID().toString();
		List<String> fileNames = new ArrayList<>();
//		Map<String, Object> result = new HashMap<>();
		int registedCount = 0;
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
			
				UserDto userDto = (UserDto) authentication.getPrincipal();
				concertMusicDto.setCmUser(userDto.getUserId());
				concertMusicDto.setCmMusic(uuid);
				concertMusicDto.setCIdx(cIdx);
				System.out.println("`111111111111111111111111111"+concertMusicDto);
				System.out.println("`222222222222222222222222222"+userDto.getUserId());
				registedCount = jamService.insertMusic(concertMusicDto);
			}

			if (registedCount > 0) {
		
				return ResponseEntity.status(HttpStatus.OK).body(null);
			} else {

				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}
