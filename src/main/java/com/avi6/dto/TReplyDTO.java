package com.avi6.dto;

import java.time.LocalDateTime;

import com.avi6.entity.TMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TReplyDTO {

    private Long reviewNum;  // 필드 이름 수정
    
    private String text;
    
    private TMember replyer;
    
    private Long bno;  // 참조 제목글 번호
    
    private LocalDateTime regDate;
    
    private LocalDateTime modDate;
}
