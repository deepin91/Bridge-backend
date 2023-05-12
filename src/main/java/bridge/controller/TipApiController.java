package bridge.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bridge.dto.TipCommentsDto;
import bridge.dto.TipDto;
import bridge.dto.UserDto;
import bridge.service.TipService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TipApiController {
	
	@Autowired
	TipService tipService;

	@PostMapping("api/inserttip")
	public ResponseEntity<Object> insertTip(@RequestBody TipDto tipDto, Authentication authentication)
			throws Exception {
		UserDto userDto = (UserDto) authentication.getPrincipal();
		tipDto.setUserId(userDto.getUserId());
		int registedCount = tipService.insertTip(tipDto);
		if (registedCount > 0) {
			return ResponseEntity.status(HttpStatus.CREATED).body(registedCount);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(registedCount);
		}

	}
	// update 에 1이 넘어오면 뷰 횟수 증가
	@GetMapping("api/tipdetail/{tb_idx}/{update}")
	public ResponseEntity<Map<String,Object>> tipDetail(@PathVariable("tb_idx") int tbIdx,@PathVariable("update") int update) throws Exception{
		TipDto tipDto = tipService.tipdetail(tbIdx);
		List<TipCommentsDto> tipCommentsDto = tipService.tipcommentslist(tbIdx);
		Map<String, Object> map = new HashMap<>();
		if(update == 1) {
			tipService.updateViews(tbIdx);	
		}
		map.put("tipDetail",tipDto);
		map.put("commentsList",tipCommentsDto);
		return ResponseEntity.status(HttpStatus.OK).body(map);
	}
	@PostMapping("api/comment")
	public ResponseEntity<Object> insertComments (@RequestBody TipCommentsDto tipCommentsDto,Authentication authentication){
		UserDto userDto = (UserDto) authentication.getPrincipal();
		tipCommentsDto.setUserId(userDto.getUserId());
		tipService.insertComment(tipCommentsDto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	@GetMapping("api/tiplist")
	public ResponseEntity<List<TipDto>> tiplist() throws Exception{
		List<TipDto> tipDto = tipService.tipList();
		return ResponseEntity.status(HttpStatus.OK).body(tipDto);
	}
	@GetMapping("api/comments/{tb_idx}")
	public  ResponseEntity<List<TipCommentsDto>> getComments(@PathVariable("tb_idx") int tbIdx){
		List<TipCommentsDto> tipCommentsDto = tipService.tipcommentslist(tbIdx);
		return ResponseEntity.status(HttpStatus.OK).body(tipCommentsDto);
	}
	@PutMapping("api/update/tip")
	public ResponseEntity<Object> updateTip(@RequestBody TipDto tipDto){
		tipService.updateTip(tipDto);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
	@DeleteMapping("api/tip/delete/{tb_idx}")
	public ResponseEntity<Object> updateTip(@PathVariable("tb_idx") int tbIdx){
		tipService.deleteTip(tbIdx);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}
}
