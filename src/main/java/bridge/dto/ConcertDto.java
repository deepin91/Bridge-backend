package bridge.dto;

import lombok.Data;

@Data
public class ConcertDto {
	private int cIdx;
	private String cPhoto;
	private String cWriter;
	
	private String title;
	private String content;
}
