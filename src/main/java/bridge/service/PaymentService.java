package bridge.service;

import java.util.List;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;
import bridge.dto.UserDto;

public interface PaymentService {

	List<PaymentDto> paymentList() throws Exception;

	void insertPayment(PaymentDto paymentDto) throws Exception;

	int paymentDetail(String userId) throws Exception;

	void doPayment(PaymentDto paymentDto);

	List<PayListDto> payListAll();

}
