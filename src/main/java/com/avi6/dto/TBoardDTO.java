package com.avi6.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TBoardDTO {

	private Long bno;

	private String title;

	private int visitCount;

	private String nickname;
	
	private String content;
	
	private Long memId;
	
	private List<String> imageURLs;
	
	@Builder.Default
    private List<TBoardImageDTO> imageDTOList = new ArrayList<>();

	private LocalDateTime regDate;

	private LocalDateTime modDate;
	
	private int replyCount;

	
}
