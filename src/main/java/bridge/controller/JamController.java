package bridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bridge.dto.TipDto;
import bridge.dto.UserDto;
import bridge.service.JamService;

@RestController
public class JamController {
	@Autowired
	JamService jamService;
	
	@PostMapping("api/insertJam")
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
}
