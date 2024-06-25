package com.avi6.repository;

import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import com.avi6.entity.TBoard;
import com.avi6.entity.TBoardImage;
import com.avi6.entity.TMember;

import jakarta.transaction.Transactional;

@SpringBootTest
public class BoardRepoTest {

	@Autowired
	private TBoardRepository tBoardRepository;

	@Autowired
	private TBoardImageRepository tBoardImageRepository;

	@Commit
	@Transactional
	@Test
	void insertTBoardAndImage() {

		IntStream.rangeClosed(1, 200).forEach(i -> {

			Long memId = (long)(Math.random() * 50) + 1;

			TMember tMember = TMember.builder().memId(memId).build();

			TBoard tBoard = TBoard.builder().title(i + "번째 게시글 제목").content(i + "번 내용 샬라샬라")
					.visitCount((int) (Math.random() * 10) + 1).member(tMember).build();

			tBoardRepository.save(tBoard);

			
			TBoardImage tBoardImage = TBoardImage.builder()
					.uuid(UUID.randomUUID().toString())
					.tBoard(tBoard)
					.imgName("forTest" + i + ".jpg")
					.build();

			tBoardImageRepository.save(tBoardImage);
		});
	}

}
