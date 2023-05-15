package bridge.dto;

import lombok.Data;

@Data
public class PartnerDetailCommentDto {

	private String pdcComment;
	private String userId;
	private int pdcIdx;
	private int pdIdx;
	private int pcIdx;
	private String commentDeletedYn;
	
	private String profileImg;
}
