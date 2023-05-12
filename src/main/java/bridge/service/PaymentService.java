package bridge.service;

import java.util.List;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;

public interface PaymentService {

	List<PaymentDto> paymentList() throws Exception;

	void insertPayment(PaymentDto paymentDto) throws Exception;

	PaymentDto paymentDetail(int paymentIdx) throws Exception;

	void doPayment(PaymentDto paymentDto);

	List<PayListDto> payListAll();

}
