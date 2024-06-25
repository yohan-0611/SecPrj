package com.avi6.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.avi6.dto.DestinationDTO;
import com.avi6.entity.DestinationEntity;

import jakarta.transaction.Transactional;

@Repository
public interface DestinationRepository extends JpaRepository<DestinationEntity, Long> {
    // 여기에 필요한 추가적인 메서드를 선언할 수 있습니다.
	
	@Query("SELECT COALESCE(MAX(d.num), 0) FROM DestinationEntity d")
	Integer findMaxNum();
	
	@Modifying
	@Transactional
	@Query("DELETE FROM DestinationEntity d WHERE d.num = :num")
	void deleteRoute(@Param("num") int num);
}
