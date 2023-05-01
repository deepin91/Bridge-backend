package bridge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import bridge.dto.ApproveResponseDto;
import bridge.dto.KakaopayDto;
import bridge.dto.ReadyResponseDto;
import bridge.mapper.BridgeMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoPayService {
	
	@Autowired
	private BridgeMapper bridgeMapper;
	
	
	// 카카오 요구 헤더 셋팅
		private HttpHeaders getHeaders() {
			HttpHeaders headers = new HttpHeaders();
		
			headers.set("Authorization", "KakaoAK c3f7d552fea955b0d2f2bf16cc4abacb"); 	//카카오 admin키
			headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
			
			return headers;
		}
	
		//카카오페이 요청 양식
	public ReadyResponseDto payReady(int totalAmount) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		
		parameters.add("cid", "TC0ONETIME"); //가맹점 테스트코드
		parameters.add("partner_order_id", "1234"); //가맹점 주문 번호
		parameters.add("partner_user_id", "브릿지"); //가맹점 회원 ID
		parameters.add("item_name", "포인트"); // 상품명
		parameters.add("quantity", "1"); // 주문 수량
		parameters.add("total_amount", String.valueOf(totalAmount)); //총 금액
		parameters.add("tax_free_amount", "0"); //상품 비과세 금액
		parameters.add("approval_url", "http://localhost:8080/order/pay/completed"); // 결제승인시 넘어갈 url
		parameters.add("cancel_url", "http://localhost:8080/order/pay/cancel"); // 결제취소시 넘어갈 url
		parameters.add("fail_url", "http://localhost:8080/order/pay/fail"); // 결제 실패시 넘어갈 url
		
//		log.info("파트너주문아이디:"+ parameters.get("partner_order_id")) ;
		//파라미터,헤더
		HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
		// 외부에 보낼 url
		RestTemplate template = new RestTemplate();
		String url = "https://kapi.kakao.com/v1/payment/ready";
        // template으로 값을 보내고 받아온 ReadyResponse값 readyResponse에 저장.
		ReadyResponseDto readyResponse = template.postForObject(url, requestEntity, ReadyResponseDto.class);
		log.info("결재준비 응답객체: " + readyResponse);
        // 받아온 값 return
		return readyResponse;
	}
	
	// 결제 승인요청 메서드
		public ApproveResponseDto payApprove(String pgToken, String tid) {
//			User user =  (User)SessionUtils.getAttribute("LOGIN_USER");
//			List<CartDto> carts = cartMapper.getCartByUserNo(user.getNo());
			
//			String order_id = "test";
			
			// request값 담기. 카카오 요청값들
			MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
			parameters.add("cid","TC0ONETIME");
//			log.info("___________________________" + readyResponseDto.getTid());
			parameters.add("tid", tid);			
			parameters.add("partner_order_id", "1234"); // 주문명
			parameters.add("partner_user_id", "가맹점회원ID");
			parameters.add("pg_token", pgToken);
			
	        // 하나의 map안에 header와 parameter값을 담아줌.
			HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
			
	        // 외부url 통신
			RestTemplate template = new RestTemplate();
			String url = "https://kapi.kakao.com/v1/payment/approve";
	        // 보낼 외부 url, 요청 메시지(header,parameter), 처리후 값을 받아올 클래스. 
			ApproveResponseDto approveResponse = template.postForObject(url, requestEntity, ApproveResponseDto.class);
			log.info("결재승인 응답객체: " + approveResponse);
			
			return approveResponse;
		};

		
		

}
