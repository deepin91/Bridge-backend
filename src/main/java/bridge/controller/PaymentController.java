package bridge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;
import bridge.mapper.PaymentMapper;
import bridge.service.PaymentService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
//@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PaymentController {

	@Autowired
	PaymentService paymentService;

	@Autowired
	PaymentMapper paymentMapper;

	/* 공지 리스트 */
	@GetMapping("/api/payment/list")
	public ResponseEntity<List<PaymentDto>> paymentList() throws Exception {
		List<PaymentDto> paymentList = paymentService.paymentList();
		if (paymentList != null && paymentList.size() > 0) {
			return ResponseEntity.status(HttpStatus.OK).body(paymentList);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	};

	// 이건 어떨때 사용?
	@GetMapping("/api/payment/detail/{paymentIdx}")
    public ResponseEntity<PaymentDto> paymentDetail(@PathVariable("paymentIdx") int paymentIdx) throws Exception {
        PaymentDto paymentDto = paymentService.paymentDetail(paymentIdx);
        if (paymentDto != null) {
            return ResponseEntity.status(HttpStatus.OK).body(paymentDto);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        }
	};

	// 아래로 수정
	/*
	 * @PostMapping("api/payment/write") public ResponseEntity<String>
	 * insertPayment(@RequestBody PaymentDto paymentDto) throws Exception{ try {
	 * paymentService.insertPayment(paymentDto); } catch (Exception e){ return
	 * ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제요청에 실패하였습니다."
	 * ); } return
	 * ResponseEntity.status(HttpStatus.OK).body("결제요청이 정상적으로 완료되었습니다."); }
	 */
	
	@PostMapping("/api/doPayment/{producer}")
    public void doPayment(@PathVariable("producer") String producer, @RequestBody PaymentDto paymentDto) throws Exception {
		
		paymentDto.setProducer(producer);

		paymentService.doPayment(paymentDto);
		paymentMapper.insertPayment();
    }
	
	//관리자용 결제내역 (모든 유저/ 모든 거래)
	@GetMapping("/api/payListAll")
	public ResponseEntity<List<PayListDto>> payListAll() throws Exception {
		List<PayListDto> payListDto = paymentService.payListAll();
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
		//회원간 거래내역
	@GetMapping("/api/payList/deal")
	public ResponseEntity<List<PayListDto>> payListDeal() throws Exception {
		List<PayListDto> payListDto = paymentMapper.payListDeal();
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
		//충전 내역
	@GetMapping("/api/payList/charge")
	public ResponseEntity<List<PayListDto>> payListCharge() throws Exception {
		List<PayListDto> payListDto = paymentMapper.payListCharge();
		return ResponseEntity.status(HttpStatus.OK).body(payListDto);
	}
	
	
    
}