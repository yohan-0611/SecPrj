package com.avi6.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.avi6.entity.TBoard;
import com.avi6.entity.TMember;
import com.avi6.entity.TReply;

@SpringBootTest
public class ReplyRepoTest {

	@Autowired
	private TReplyRepository tReplyRepository;

	@Test
	void insertReview() {
		IntStream.rangeClosed(1, 300).forEach(i ->{
			
			long bno = (long)(Math.random() * 200) + 1;
			
			TMember tMember = TMember.builder()
					.memId((long)(i - 1) % 50 + 1)
					.build();
			
			TBoard tBoard = TBoard.builder()
					.bno(bno)
					.build();
			
	        TReply tReply = TReply.builder()
	               .text("댓글 샬라 샬라라랄~~" + i)
	               .board(tBoard)
	               .member(tMember)
	               .build();
	         
	        tReplyRepository.save(tReply);
	    });
	}
}
