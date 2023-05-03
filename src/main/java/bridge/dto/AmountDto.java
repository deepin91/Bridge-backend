package bridge.dto;

import lombok.Data;

@Data
public class AmountDto {
	
	private int total; //총 결제 금액
	private int tax_free; //비과세 금액
	private int vat; //부가세 금액
	private int point; // 사용한포인트
	private int discount; //할인금액

}
