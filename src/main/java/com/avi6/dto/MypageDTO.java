package com.avi6.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MypageDTO {//마이페이지 DTO

	private Long memId;
	
	private String email;
	
	private String nickname;
	
	private String password;
	
	private LocalDateTime regDate;
	
	//위시리스트
	private boolean wishList; 
	
}
