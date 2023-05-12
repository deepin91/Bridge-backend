package bridge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;
import bridge.mapper.PaymentMapper;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentMapper paymentMapper;
    
    @Override
    public List<PaymentDto> paymentList() throws Exception {
        return paymentMapper.paymentList();
    }

    @Override
    public void insertPayment(PaymentDto paymentDto) throws Exception {
        paymentMapper.insertPayment(paymentDto);
        
    }

    @Override
    public PaymentDto paymentDetail(int paymentIdx) throws Exception {
        return paymentMapper.paymentDetail(paymentIdx);
    }

	@Override
	public void doPayment(PaymentDto paymentDto) {
		paymentMapper.doPayment(paymentDto);
		paymentMapper.updatePoint(paymentDto.getUsepoint());
	}

	@Override
	public List<PayListDto> payListAll() {
		return paymentMapper.payListAll();
	}

}