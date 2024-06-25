package com.avi6.service;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.stereotype.Service;

import com.avi6.dto.DestinationDTO;
import com.avi6.dto.MypageDTO;
import com.avi6.entity.DestinationEntity;
import com.avi6.entity.TMember;
import com.avi6.repository.DestinationRepository;
import com.avi6.repository.TMemberRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TMemberServiceImpl implements TMemberService{
	
	private final TMemberRepository tMemberRepository;
	
	private final DestinationRepository destinationRepository;
	
	@Override
	public List<TMember> getTmember(MypageDTO tMemberDTO) {
	
		return tMemberRepository.findAll();
	}


	@Override
	public TMember findMemberById(Long memId) {
		return tMemberRepository.findById(memId)
                .orElse(null);
	}
	
	
	


	@Override
	public void delDest(Long memId) {
		tMemberRepository.deleteById(memId);
		
	}

	@Override
	public void article(MypageDTO tMemberDTO) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public List<TMember> updateNickname(MypageDTO tMemberDTO) {
		// TODO Auto-generated method stub
		return null;
	}


	/*
	 * @Override
	 * 
	 * @Transactional public List<TMember> updateNickname(MypageDTO tMemberDTO) { //
	 * 로깅 (디버깅 목적으로 유지할 수 있음)
	 * System.out.println("Updating nickname for member with ID: " +
	 * tMemberDTO.getMemId());
	 * 
	 * // 닉네임을 업데이트합니다. tMemberRepository.updateNickname(tMemberDTO.getMemId(),
	 * tMemberDTO.getNickname());
	 * 
	 * // 모든 회원 목록을 반환합니다. return tMemberRepository.findAll(); }
	 */

	
	
	/*
	 * @Override public void updateNickname(TMemberDTO tMemberDTO) { TMember tMember
	 * = tMemberRepository.updateNicknameById(tMemberDTO.getMemId(),
	 * tMemberDTO.getNickname()) .orElseThrow(() -> new
	 * IllegalArgumentException("Invalid id"));
	 * tMember.setNickname(tMember.getMemId()); tMemberRepository.save(tMember); }
	 */
	


	

}
