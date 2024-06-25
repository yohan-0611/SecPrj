package com.avi6.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.avi6.entity.TBoard;
import com.avi6.entity.TReply;

import jakarta.transaction.Transactional;

public interface TReplyRepository extends JpaRepository<TReply, Long>{

	//댓글 삭제 선언
	/*
	 * @Modifying
	 * 
	 * @Transactional //아래 쿼리는 제목글 삭제시 댓글 삭제 쿼리
	 * 
	 * @Query("Delete from Reply r where r.board.bno = :bno") void
	 * deleteByBno(@Param("bno") Long bno);
	 */
	
	//List<TReply> getRepliesByBoardOrderByReviewNum(TBoard tBoard);

	@Query("SELECT COUNT(r) FROM TReply r WHERE r.board.bno = :bno")
    int countByTBoardBno(@Param("bno") Long bno);

	@Query("SELECT r FROM TReply r JOIN FETCH r.member WHERE r.board.bno = :bno")
    List<TReply> findByTBoardBno(@Param("bno") Long bno);
}
