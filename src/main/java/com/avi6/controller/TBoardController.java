package com.avi6.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.avi6.dto.TBoardDTO;
import com.avi6.dto.TPageRequestDTO;
import com.avi6.dto.TPageResultDTO;
import com.avi6.dto.TReplyDTO;
import com.avi6.repository.TBoardRepository;
import com.avi6.service.TBoardService;
import com.avi6.service.TReplyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class TBoardController {

    @Autowired
    private TBoardRepository tBoardRepository;

    private final TBoardService tboardService;
    
    private final TReplyService tReplyService;

    @GetMapping("/read")
    public String read(@RequestParam("bno") Long bno, @RequestParam("page") int page, Model model) {
    	
    	// 조회 수 증가
        tboardService.incrementVisitCount(bno);
    	
        TBoardDTO tBoardDTO = tboardService.getBoard(bno);
        
        // 댓글 목록 가져오기
        List<TReplyDTO> replies = tReplyService.getRepliesByBoardBno(bno);
        
        model.addAttribute("dto", tBoardDTO);
        model.addAttribute("pageRequestDTO", TPageRequestDTO.builder().page(page).build());
        model.addAttribute("replies", replies);  // 댓글 목록 추가
        
        return "board/read";
    }
    
    @GetMapping("/replies/board/{bno}")
    public List<TReplyDTO> getReplies(@PathVariable Long bno) {
        return tReplyService.getRepliesByBoardBno(bno);
    }

    @GetMapping("/modify")
    public String modify(@RequestParam("bno") Long bno, @ModelAttribute("pageRequestDTO") TPageRequestDTO pageRequestDTO, Model model) {
        TBoardDTO tBoardDTO = tboardService.getBoard(bno);
        model.addAttribute("dto", tBoardDTO);
        model.addAttribute("pageRequestDTO", pageRequestDTO);
        return "board/modify";
    }

    @PostMapping("/modify")
    public String modifyPost(TBoardDTO tBoardDTO, @ModelAttribute("pageRequestDTO") TPageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        System.out.println("글 수정 요청 : " + tBoardDTO);
        tboardService.updateArticle(tBoardDTO);
        redirectAttributes.addAttribute("page", pageRequestDTO.getPage());
        redirectAttributes.addAttribute("bno", tBoardDTO.getBno());
        return "redirect:/board/read";
    }

    @GetMapping("/list")
    public String list(TPageRequestDTO tPageRequestDTO, Model model) {
    	TPageResultDTO<TBoardDTO, Object[]> result = tboardService.getList(tPageRequestDTO);
    	// 로그 추가
        for (TBoardDTO dto : result.getDtoList()) {
            System.out.println("Board ID: " + dto.getBno() + ", Reply Count: " + dto.getReplyCount());
        }
        model.addAttribute("result", result);
        
        return "list";
    }

    @DeleteMapping("/{bno}")
    public ResponseEntity<String> delArticle(@PathVariable("bno") Long bno) {
        try {
            // 댓글부터 삭제
            tBoardRepository.delByBno(bno);

            // 글 삭제
            tBoardRepository.deleteById(bno);

            return new ResponseEntity<>("삭제되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("삭제 실패: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public String list0() {
        return "redirect:/board";
    }
    
    @PostMapping("/increment-visit")
    public ResponseEntity<String> incrementVisit(@RequestParam Long bno) {
        try {
            tboardService.incrementVisitCount(bno);
            return new ResponseEntity<>("Visit count incremented.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error incrementing visit count: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
