package bridge.dto;

import lombok.Data;

@Data
public class UserProfileDto {

	private int userProfileIdx;
	//인증 지금 없음 => 빼는건지 기능 넣는건지 결정 필요
	private boolean userCertification;
	
	private String userId;
	private String userPosition;
	private String userIntroduction;
	private String profileImg;
	private String bannerImg;
	private String userSite;
	private String userPhoto;
<<<<<<< HEAD
	private String userTag1;
	private String userTag2;
	private String userTag3;
	private String userTag;
	private String userMusic;

=======
<<<<<<< HEAD
//<<<<<<< HEAD
//	private String userTag1;
//	private String userTag2;
//	private String userTag3;
//=======
	private String userTag;
	private String userMusic;
//>>>>>>> 2d43ffad7d15c460c680e7cccbc102d9d168520e
=======

	private String userTag;
	private String userMusic;

>>>>>>> 0f976816521af5fb2edd3b2e7af157318a8cd54d
>>>>>>> f0fd70131322e3756f93a850234c6341f924b9d3
}
