package bridge.dto;

import lombok.Data;

@Data
public class PartnerDetailDto {

	private int pdIdx;
	private String userId1;
	private String userId2;
	private int pdMoney;
	private String userPhoto;
	private String userTag;
}
