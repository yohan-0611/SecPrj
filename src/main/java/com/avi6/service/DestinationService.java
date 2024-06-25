package com.avi6.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.avi6.dto.DestinationDTO;
import com.avi6.entity.DestinationEntity;

import jakarta.transaction.Transactional;

@Transactional
public interface DestinationService {

	List<DestinationEntity> saveDestinations(List<DestinationDTO> destinationDTOList);

	List<DestinationEntity> getDestinations(DestinationDTO destinationDTO);

	List<String> getNamesByLocal(List<DestinationDTO> destinationDTOList, String local);

	// 삭제
	void deleteRoute(int num);

	// 수정
	void articleRoute(DestinationDTO destinationDTO);

	public void delWish(Long wishId);

}
