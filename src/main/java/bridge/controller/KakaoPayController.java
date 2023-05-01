package bridge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import bridge.dto.ApproveResponseDto;
import bridge.dto.ReadyResponseDto;
import bridge.service.KakaoPayService;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
//@SessionAttributes("tid") 
public class KakaoPayController {
	// 카카오페이
	// , Order order, Model model
	@Autowired
	private KakaoPayService kakaopayService;

	@GetMapping("/order/pay")
	public @ResponseBody ReadyResponseDto payReady(@RequestParam(name = "total_amount") int totalAmount, Model model) {
		log.info("주문가격:" + totalAmount);
		// 카카오 결제 준비하기 - 결제요청 service 실행.
		ReadyResponseDto readyResponse = kakaopayService.payReady(totalAmount);
		// 요청처리후 받아온 결재고유 번호(tid)를 모델에 저장
		model.addAttribute("tid", readyResponse.getTid());	

//		this.tid = readyResponse.getTid();
		log.info("11111111111결재고유 번호: " + readyResponse.getTid());
		// Order정보를 모델에 저장
//		model.addAttribute("order", order);
		

		return readyResponse; // 클라이언트에 보냄.(tid,next_redirect_pc_url이 담겨있음.)
	}


//	private String tid = "";

	@GetMapping("/order/pay/completed")
	public String payCompleted(@RequestParam("pg_token") String pgToken,@ModelAttribute("tid") String tid) {

		
		log.info("22222222222222결제승인 요청을 인증하는 토큰: " + pgToken);
//		log.info("주문정보: " + order);		

		// String tid = "";
//		log.info("tid >>>>>>>>>>>" + this.tid);
		// 추가
//		model.addAttribute("info",  kakaopayService.payApprove(pgToken));

		// 카카오 결제 요청하기
		ApproveResponseDto approveResponse = kakaopayService.payApprove(pgToken, tid);

		// 5. payment 저장
		// orderNo, payMathod, 주문명.
		// - 카카오 페이로 넘겨받은 결재정보값을 저장.
//		Payment payment = Payment.builder() 
//				.paymentClassName(approveResponse.getItem_name())
//				.payMathod(approveResponse.getPayment_method_type())
//				.payCode(tid)
//				.build();
//		
//		orderService.saveOrder(payment);

		return "redirect:/api/announcementList";
//		return 
	}

	// 결제 실패시 실행 url
	@GetMapping("/order/pay/fail")
	public String payFail() {
		return "redirect:/order/pay";
	}
}
