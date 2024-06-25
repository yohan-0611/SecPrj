package com.avi6.service;

import java.util.List;

import com.avi6.dto.TMemberDTO;
import com.avi6.dto.TReplyDTO;
import com.avi6.entity.TBoard;
import com.avi6.entity.TMember;
import com.avi6.entity.TReply;

public interface TReplyService {

	default TReply dtoToEntity(TReplyDTO dto) {
		
		TMember tMember = TMember.builder()
				.memId((long)Math.random()*50 + 1)
				.build();
		
		TBoard tBoard = TBoard.builder()
				.bno(dto.getBno())
				.build();
		
		TReply tReply = TReply.builder()
				.reviewNum(dto.getReviewNum())
				.text(dto.getText())
				.board(tBoard)
				.build();
		
		return tReply;
				
	}
	
	default TReplyDTO entityToDTO(TReply tReply) {
		
		TReplyDTO tReplyDTO = TReplyDTO.builder()
				.reviewNum(tReply.getReviewNum())
				.text(tReply.getText())
				.replyer(tReply.getMember())
				.regDate(tReply.getRegDate())
				.modDate(tReply.getModDate())
				.build();
		
		return tReplyDTO;
	}
	
	//댓글 등록 메서드
	Long register(TReplyDTO tReplyDTO);
	
	//댓글 list 로 받아오기
	//List<TReplyDTO> getList(Long bno);
	
	//수정 메서드
	void modify(TReplyDTO tReplyDTO, TMemberDTO tMemberDTO);
	
	//삭제 메서드
	void remove(Long reviewNum);
	
    List<TReplyDTO> getRepliesByBoardBno(Long bno);

	void saveReply(TReplyDTO replyDTO);
	
}
