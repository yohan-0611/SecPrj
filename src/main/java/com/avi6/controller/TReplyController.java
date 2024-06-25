package com.avi6.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avi6.dto.TMemberDTO;
import com.avi6.dto.TReplyDTO;
import com.avi6.service.TReplyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/reply")
@RequiredArgsConstructor
public class TReplyController {

    private final TReplyService tReplyService;
    
    @PostMapping("/add")
    public ResponseEntity<String> addReply(@RequestBody TReplyDTO replyDTO) {
        tReplyService.saveReply(replyDTO);
        return new ResponseEntity<>("댓글이 작성되었습니다.", HttpStatus.OK);
    }
    
    @GetMapping("/list")
    public ResponseEntity<List<TReplyDTO>> getReplies(@RequestParam("bno") Long bno) {
        List<TReplyDTO> replies = tReplyService.getRepliesByBoardBno(bno);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }
    
    @PostMapping("")
    public ResponseEntity<Long> register(@RequestBody TReplyDTO tReplyDTO) {
        System.out.println("신규 댓글 처리요청됨.");
        Long reviewNum = tReplyService.register(tReplyDTO);
        
        return new ResponseEntity<>(reviewNum, HttpStatus.OK);
    }
    

    @PutMapping("/{reviewNum}")
    public ResponseEntity<String> modify(@RequestBody TReplyDTO tReplyDTO, TMemberDTO tMemberDTO){
        System.out.println("댓글 수정 요청됨.");
        tReplyService.modify(tReplyDTO, tMemberDTO);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
    
    @DeleteMapping("/{reviewNum}")
    public ResponseEntity<String> remove(@PathVariable("reviewNum") Long reviewNum){
        System.out.println("댓글 삭제 요청됨.");
        tReplyService.remove(reviewNum);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
    
}
