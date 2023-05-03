package bridge.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bridge.dto.NoticeDto;
import bridge.dto.UserDto;
import bridge.service.JpaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NoticeController {

	@Autowired
	JpaService jpaService;

	/* 공지 리스트 */
	@GetMapping("/api/notice")
	public ResponseEntity<List<NoticeDto>> noticeList() throws Exception {
		List<NoticeDto> noticeList = jpaService.noticeList();
		if (noticeList != null && noticeList.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(noticeList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	
	/* 공지 작성 */
	@PostMapping("/api/notice/write")
	public ResponseEntity<String> insertNotice(@RequestBody NoticeDto noticeDto) throws Exception {
		try {
			jpaService.insertNotice(noticeDto);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("등록에 실패하였습니다.");
		}
		return ResponseEntity.status(HttpStatus.OK).body("정상적으로 등록되었습니다.");
	}

	
	/* 공지 상세 */
	@GetMapping("/api/notice/detail/{noticeIdx}")
	public ResponseEntity<NoticeDto> noticeDetail(@PathVariable("noticeIdx") int noticeIdx) throws Exception {
		NoticeDto noticeDto = jpaService.noticeDetail(noticeIdx);
		if (noticeDto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(noticeDto);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}

	/* 공지 수정 */
	@PutMapping("/api/notice/{noticeIdx}")
	public ResponseEntity<String> updateNotice(@PathVariable("noticeIdx") int noticeIdx, 
			@RequestBody NoticeDto noticeDto, Authentication authentication) throws Exception {
		try {
			NoticeDto detail = jpaService.noticeDetail(noticeIdx);
			UserDto userDto = (UserDto) authentication.getPrincipal();	
			if (  detail.getUserId().equals(userDto.getUserId()) || userDto.getUserId().equals("admin")) {
				noticeDto.setNoticeIdx(noticeIdx);
				int updatedCount = jpaService.updateNotice(noticeDto);
				if (updatedCount != 1) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("수정에 실패했습니다");
				} else {
					return ResponseEntity.status(HttpStatus.OK).body("정상 수정되었습니다");
				}

			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성자만 수정 가능합니다");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 해주세요");
		}
	}

	/* 공지 삭제 */
	@DeleteMapping("/api/notice/{noticeIdx}")
	public ResponseEntity<String> deleteNotice(@PathVariable ("noticeIdx") int noticeIdx, Authentication authentication) throws Exception{
		try {
			UserDto userDto = (UserDto) authentication.getPrincipal();
			NoticeDto noticeDto = jpaService.noticeDetail(noticeIdx);
			
			log.debug(">>>>>>>>>>>>>>>" +userDto.getUserId());
			if (noticeDto.getUserId().equals(noticeDto.getWriter()) || noticeDto.getUserId().equals("admin") ) {
				int deletedCount = jpaService.deleteNotice(noticeIdx);
				if (deletedCount != 1) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("삭제에 실패했습니다");
				} else {
					return ResponseEntity.status(HttpStatus.OK).body("정상 삭제되었습니다");
				}
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("작성자만 삭제 가능합니다");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인 해주세요");
		}
	}
}
	
//	@GetMapping("/api/notice/search")
//	public ResponseEntity<List<NoticeDto>> getSerchList(@RequestParam(value = "search") String search) {
//	        try {
//	            List<NoticeDto> getSerchList = jpaService.getSerchList(word);
//	            log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<" + jpaService.getSerchList(word));
//	            return ResponseEntity.status(HttpStatus.OK).body(getSerchList);
//	        } catch (Exception exception) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//	        }
//	    }
//}
	


