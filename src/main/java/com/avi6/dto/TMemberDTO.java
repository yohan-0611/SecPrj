package com.avi6.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TMemberDTO {//회원 DTO

	private Long memId;
	
	private String email;
	
	private String nickname;
	
	private String aboutMe;
	
	private String memberImage;
	
	private String password;
	
	private LocalDateTime regDate;
		
}