package bridge.dto;

import lombok.Data;

@Data
public class TagDto {
	private String[] tags;
	private String tag;
//	private String userId;
	private int userProfileIdx;
}
