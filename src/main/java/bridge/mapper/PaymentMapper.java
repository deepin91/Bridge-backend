package bridge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import bridge.dto.PayListDto;
import bridge.dto.PaymentDto;
import bridge.dto.UserDto;

@Mapper
public interface PaymentMapper {

    List<PaymentDto> paymentList()throws Exception;
    void insertPayment(PaymentDto paymentDto)throws Exception;
    int paymentDetail(String userId)throws Exception;
	void doPayment(PaymentDto paymentDto);
	void updatePoint(int i);
	void updatePartnerMoney();
	int insertPayment();
	List<PayListDto> payListAll();
	List<PayListDto> payListDeal();
	List<PayListDto> payListCharge();
	
	

}
