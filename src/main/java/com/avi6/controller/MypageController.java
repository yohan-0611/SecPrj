package com.avi6.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.avi6.dto.DestinationDTO;
import com.avi6.dto.MypageDTO;
import com.avi6.dto.TPageRequestDTO;
import com.avi6.entity.TMember;
import com.avi6.repository.DestinationRepository;
import com.avi6.service.DestinationService;
import com.avi6.service.TBoardService;
import com.avi6.service.TMemberService;

import lombok.RequiredArgsConstructor;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class MypageController {
	
	private final TBoardService tBoardService;
	private final DestinationService destinationService;
	private final TMemberService tMemberService;
	private final DestinationRepository destinationRepository;

	@DeleteMapping("/delete/{num}")
    public ResponseEntity<String> deleteDestination(@PathVariable("num") int num) {
        try {
            destinationRepository.deleteRoute(num);
            return new ResponseEntity<>("Destination deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete destination: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	


	@PutMapping("/article/{nickname}")
    public ResponseEntity<String> articleRoute(@RequestBody DestinationDTO destinationDTO) {
        try {
            destinationService.articleRoute(destinationDTO);
            return new ResponseEntity<>("Route updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update route: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
	@PutMapping("/updateNickname")
	public ResponseEntity<String> updateNickname(@RequestBody MypageDTO tMemberDTO){
		System.out.println("수정요청");
		tMemberService.updateNickname(tMemberDTO);
		return new ResponseEntity<>("seccess",HttpStatus.OK );
	}

    @GetMapping("/mypage")
    public String getDest(TPageRequestDTO tPageRequestDTO,
                          DestinationDTO destinationDTO, Model model, MypageDTO tMemberDTO) {
        model.addAttribute("dest", destinationService.getDestinations(destinationDTO));
        model.addAttribute("chan", tMemberService.getTmember(tMemberDTO));
        model.addAttribute("result", tBoardService.getList(tPageRequestDTO));

        List<TMember> tMember = tMemberService.getTmember(tMemberDTO);
        
        Long memId = tMember.get(0).getMemId();
        model.addAttribute("memId", memId);
        return "mypage";
    }
}
