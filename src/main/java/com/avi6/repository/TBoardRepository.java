package com.avi6.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.avi6.entity.TBoard;

public interface TBoardRepository extends JpaRepository<TBoard, Long>, TSearchBoardRepository {



	@Query("SELECT b, i, m FROM TBoard b " +
	           "LEFT JOIN TBoardImage i ON i.tBoard = b " +
	           "LEFT JOIN TMember m ON b.member = m")
	Page<Object[]> getListPage(Pageable pageable);

	
	@Query("SELECT b, i, m, b.visitCount FROM TBoard b " + "LEFT JOIN TBoardImage i ON i.tBoard = b "
			+ "LEFT JOIN TMember m ON b.member = m " + "WHERE b.bno = :bno")
	List<Object[]> getBoardWithAll(@Param("bno") Long bno);

	
	@Query("SELECT b, m, i FROM TBoard b " +
	           "LEFT JOIN b.member m " +
	           "LEFT JOIN b.imageList i " +
	           "WHERE b.bno = :bno")
	List<Object[]> getBoardWithBno(@Param("bno") Long bno);


	@Modifying 
	@Transactional
	@Query("Delete from TReply r where r.board.bno =:bno")
	void delByBno(@Param("bno") Long bno);
	
	
	@Query(value = "SELECT b, m, i, COUNT(r) FROM TBoard b " +
            "LEFT JOIN b.member m " +
            "LEFT JOIN b.imageList i " +
            "LEFT JOIN TReply r ON r.board = b " +
            "GROUP BY b, m, i",
    countQuery = "SELECT COUNT(b) FROM TBoard b")
	Page<Object[]> getBoardWithReplyCnt(Pageable pageable);	
	
	
	@Query("SELECT COUNT(r) FROM TReply r WHERE r.board.bno = :bno")
    int getReplyCountByBoardId(@Param("bno") Long bno);
	
}
