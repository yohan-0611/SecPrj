package com.avi6.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import com.avi6.dto.TBoardDTO;
import com.avi6.dto.TBoardImageDTO;
import com.avi6.dto.TPageRequestDTO;
import com.avi6.dto.TPageResultDTO;
import com.avi6.entity.TBoard;
import com.avi6.entity.TBoardImage;
import com.avi6.entity.TMember;

public interface TBoardService {
	
	//TBoardDTO 리턴 메서드 선언
	TBoardDTO getBoard(@Param("bno") Long bno);
	
    //등록 메서드 선언
    Long register(TBoardDTO tBoardDTO);
    
    //리스트 항목 리턴하는 메서드
    TPageResultDTO<TBoardDTO, Object[]> getList(TPageRequestDTO tPageRequestDTO);
    
    //조회수 증가시키는 메서드
    public void incrementVisitCount(Long bno);
    
    void updateArticle(TBoardDTO tBoardDTO);
    
    void delArticle(Long bno);
    
    //DTO -> Entity
    default Map<String, Object> dtoToEntity(TBoardDTO tBoardDTO) {
        
    	Map<String, Object> entityMap = new HashMap<>();
    	
        TMember tMember = TMember.builder()
        		.memId(tBoardDTO.getMemId())
        		.nickname(tBoardDTO.getNickname())
        		.build();
        
        TBoard tBoard = TBoard.builder()
                .bno(tBoardDTO.getBno())
                .title(tBoardDTO.getTitle())
                .content(tBoardDTO.getContent())
                .member(tMember)
                .build();
        
        entityMap.put("tBoard", tBoard);
        
        List<TBoardImageDTO> tImageDTOList = tBoardDTO.getImageDTOList();
        
        if(tImageDTOList != null && tImageDTOList.size()>0) {
        	List<TBoardImage> imageEntities = tImageDTOList.stream()
        			.map(tBoardImageDTO -> {
        				TBoardImage tBoardImage = TBoardImage.builder()
        						.path(tBoardImageDTO.getPath())
        						.imgName(tBoardImageDTO.getImgName())
        						.uuid(tBoardImageDTO.getUuid())
        						.tBoard(tBoard)
        						.build();
        				
        				return tBoardImage;
        				
        			}).collect(Collectors.toList());
        	
        	entityMap.put("imageList", imageEntities);
        }

        return entityMap;
    }
    
    
    //Entity -> DTO
    default TBoardDTO entityToDTO(TBoard tBoard, List<TBoardImage> tBoardImages, TMember tMember, Long visitCount, Long replyCount) {
        TBoardDTO tBoardDTO = TBoardDTO.builder()
                .bno(tBoard.getBno())
                .title(tBoard.getTitle())
                .content(tBoard.getContent())
                .modDate(tBoard.getModDate())
                .regDate(tBoard.getRegDate())
                .nickname(tMember.getNickname())
                .visitCount(tBoard.getVisitCount())
                .replyCount(replyCount.intValue())
                .build();
        
        List<TBoardImageDTO> tBoardImageDTOs = tBoardImages.stream()
        		.map(tImage -> {
        			TBoardImageDTO tBoardImageDTO = TBoardImageDTO.builder()
        					.inum(tImage.getInum())
        					.path(tImage.getPath())
        					.uuid(tImage.getUuid())
        					.imgName(tImage.getImgName())
        					.build();
        			
        			return tBoardImageDTO;
        			
        		}).collect(Collectors.toList());
        
        tBoardDTO.setImageDTOList(tBoardImageDTOs);
        tBoardDTO.setVisitCount(visitCount.intValue());
        //tBoardDTO.getMemId(member.getMemId());
        return tBoardDTO;
    }
    
    public TMember findMemberById(Long memId);

	TBoardDTO get(Long bno);
    
}