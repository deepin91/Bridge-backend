package bridge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;

@Mapper
public interface PaymentMapper {

    List<PaymentDto> paymentList()throws Exception;
    void insertPayment(PaymentDto paymentDto)throws Exception;
    PaymentDto paymentDetail(int paymentIdx)throws Exception;
	void doPayment(PaymentDto paymentDto);
	void updatePoint(int i);
	int insertPayment();
	List<PayListDto> payListAll();
	List<PayListDto> payListDeal();
	List<PayListDto> payListCharge();
	

}
