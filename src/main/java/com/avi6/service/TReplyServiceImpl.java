package com.avi6.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avi6.dto.TMemberDTO;
import com.avi6.dto.TReplyDTO;
import com.avi6.entity.TBoard;
import com.avi6.entity.TMember;
import com.avi6.entity.TReply;
import com.avi6.repository.TBoardRepository;
import com.avi6.repository.TMemberRepository;
import com.avi6.repository.TReplyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TReplyServiceImpl implements TReplyService{

	private final TReplyRepository tReplyRepository;
	
	private final TBoardRepository tBoardRepository;
	
	private final TMemberRepository tMemberRepository;
	
	@Override
	public Long register(TReplyDTO tReplyDTO) {
        // 추가할 댓글 엔티티 생성 및 저장 로직 작성
        TBoard board = tBoardRepository.findById(tReplyDTO.getBno()).orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        TMember member = tMemberRepository.findById(tReplyDTO.getReplyer().getMemId()).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        TReply reply = TReply.builder()
                .text(tReplyDTO.getText())
                .board(board)
                .member(member)
                .build();

        tReplyRepository.save(reply);
        return reply.getReviewNum();
    }

	public void modify(TReplyDTO tReplyDTO, TMemberDTO tMemberDTO) {
        // 댓글 수정 로직 작성
        TReply reply = tReplyRepository.findById(tReplyDTO.getReviewNum()).orElseThrow(() -> new IllegalArgumentException("Invalid review number"));
        reply.setText(tReplyDTO.getText());
        tReplyRepository.save(reply);
    }

    public void remove(Long reviewNum) {
        // 댓글 삭제 로직 작성
        tReplyRepository.deleteById(reviewNum);
    }

	@Override
	public List<TReplyDTO> getRepliesByBoardBno(Long bno) {
        List<TReply> replies = tReplyRepository.findByTBoardBno(bno);
        return replies.stream().map(reply -> TReplyDTO.builder()
                .reviewNum(reply.getReviewNum())
                .text(reply.getText())
                .replyer(reply.getMember())
                .bno(reply.getBoard().getBno())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .build()).collect(Collectors.toList());
    }

	@Override
    public void saveReply(TReplyDTO replyDTO) {
        TBoard board = tBoardRepository.findById(replyDTO.getBno()).orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        TMember member = tMemberRepository.findById(replyDTO.getReplyer().getMemId()).orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        TReply reply = TReply.builder()
                .text(replyDTO.getText())
                .board(board)
                .member(member)
                .build();

        tReplyRepository.save(reply);
    }
	
}
