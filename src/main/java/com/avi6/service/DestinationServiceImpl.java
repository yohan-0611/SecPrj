package com.avi6.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avi6.dto.DestinationDTO;
import com.avi6.entity.DestinationEntity;
import com.avi6.repository.DestinationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;
    
    private Integer maxNum;

    @Override
    public List<DestinationEntity> getDestinations(DestinationDTO destinationDTO) {
        return destinationRepository.findAll();
    }

    @Override
    public List<String> getNamesByLocal(List<DestinationDTO> destinationDTOList, String local) {
        return destinationDTOList.stream()
                .filter(dto -> local.equals(dto.getLocal()))
                .map(DestinationDTO::getName)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<DestinationEntity> saveDestinations(List<DestinationDTO> destinationDTOList) {
        List<DestinationEntity> savedDestinations = new ArrayList<>();
        maxNum = destinationRepository.findMaxNum() + 1;
        for (DestinationDTO destinationDTO : destinationDTOList) {
            DestinationEntity destinationEntity = new DestinationEntity();
            destinationEntity.setPlace_id(destinationDTO.getPlace_id());
            destinationEntity.setName(destinationDTO.getName());
            destinationEntity.setTravelModeEnum(destinationDTO.getTravelModeEnum());
            destinationEntity.setOrigin_lat(destinationDTO.getOrigin_lat());
            destinationEntity.setOrigin_lng(destinationDTO.getOrigin_lng());
            destinationEntity.setEmail("qwer1234@naver.com");
            destinationEntity.setLocal(destinationDTO.getLocal());
            destinationEntity.setNum(maxNum);

            DestinationEntity savedDestination = destinationRepository.save(destinationEntity);
            savedDestinations.add(savedDestination);
        }
        return savedDestinations;
    }

    @Override
    @Transactional
    public void deleteRoute(int num) { 
        destinationRepository.deleteAll();
    }

    @Override
    @Transactional
    public void articleRoute(DestinationDTO destinationDTO) {
        DestinationEntity destinationEntity = destinationRepository.findById(destinationDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid id"));
        destinationEntity.setLocal(destinationDTO.getLocal());
        destinationEntity.setName(destinationDTO.getName());
        destinationRepository.save(destinationEntity);
    }

	@Override
	public void delWish(Long wishId) {
		// TODO Auto-generated method stub
		
	}
}
