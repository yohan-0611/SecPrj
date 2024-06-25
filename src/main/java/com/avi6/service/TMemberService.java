package com.avi6.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.avi6.dto.DestinationDTO;
import com.avi6.dto.TBoardDTO;
import com.avi6.dto.TBoardImageDTO;
import com.avi6.dto.MypageDTO;
import com.avi6.entity.DestinationEntity;
import com.avi6.entity.TBoard;
import com.avi6.entity.TBoardImage;
import com.avi6.entity.TMember;

import com.avi6.repository.TMemberRepository;

import jakarta.transaction.Transactional;

@Transactional
public interface TMemberService {
	
	
	
	default MypageDTO entityToDTO(TMember tMember) {
		MypageDTO tMemberDTO = MypageDTO.builder()
				.memId(tMember.getMemId())
				.email(tMember.getEmail())
				.password(tMember.getPassword())
				.nickname(tMember.getNickname())
				.build();
				
				return tMemberDTO;
	}
	
	
	
	
	
    public TMember findMemberById(Long memId);
    



	List<TMember> updateNickname(MypageDTO tMemberDTO);


	//수정
		void article(MypageDTO tMemberDTO);


	void delDest(Long memId);





	public List<TMember> getTmember(MypageDTO tMemberDTO);
    
    

}
