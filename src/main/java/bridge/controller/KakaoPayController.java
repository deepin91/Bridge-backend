package bridge.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import bridge.dto.ApproveResponseDto;
import bridge.dto.ReadyResponseDto;
import bridge.dto.UserDto;
import bridge.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class KakaoPayController {

	@Autowired
	private KakaoPayService kakaopayService;
	
	@GetMapping("/order/pay/{totalAmount}/{userId}")
	public @ResponseBody ReadyResponseDto payReady(@PathVariable("totalAmount") int totalAmount, Model model, @PathVariable("userId") String userId) {
//		UserDto userDto = (UserDto) authentication.getPrincipal();
		UserDto userDto = new UserDto();
		userDto.setUserId(userId);
		ReadyResponseDto readyResponse = kakaopayService.payReady(totalAmount,userDto);
		model.addAttribute("tid", readyResponse.getTid());
		model.addAttribute("partner_order_id", readyResponse.getPartner_order_id());

		log.info("11111111111결재고유 번호: " + readyResponse.getTid());
		log.info("22222222222파트너: " + readyResponse.getPartner_order_id());
		log.info("33333333333333"+readyResponse.getCreated_at());
		
		return readyResponse;
	}


	@GetMapping("/order/pay/completed")
	public void payCompleted(@RequestParam("pg_token") String pgToken, HttpServletResponse response) throws IOException{
		log.info("22222222222222결제승인 요청을 인증하는 토큰: " + pgToken);

		ApproveResponseDto approveResponse = kakaopayService.payApprove(pgToken);
		System.out.println(">>>>>>>>>>>>>>>>>>" + approveResponse);

		response.sendRedirect("http://localhost:3000/19");
	}

//	@GetMapping("/test1")
//	public void test()  {
//		response.sendRedirect("http://localhost:3000/1");
//	}

	@GetMapping("/order/pay/cancel")
	public String payCancel() {
		return "결제취소";
	}

	// 결제 실패시 실행 url
	@GetMapping("/order/pay/fail")
	public String payFail() {
		return "redirect:/order/pay";
	}
	
//	@GetMapping("/test")
//	public ResponseEntity<String> test (Authentication authentication) {
//		UserDto userDto = (UserDto) authentication.getPrincipal();
//		return ResponseEntity.status(HttpStatus.OK).body(userDto.getUserId());
//	
//	}
}
