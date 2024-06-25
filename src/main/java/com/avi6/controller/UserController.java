package com.avi6.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.avi6.dto.UserDTO;
import com.avi6.service.UserService;
import com.avi6.user.DuplicateEmailException;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("user", new UserDTO()); // 빈 UserDTO 객체를 생성하여 폼에 바인딩
        return "signup"; // 회원가입 페이지 보여주기
    }

    @PostMapping("/signup")
    public String signUp(@ModelAttribute("user") UserDTO userDTO, Model model) {
        try {
            userService.save(userDTO); // 사용자 저장 시 중복 이메일 여부 확인은 UserService에서 처리됨
            return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 이동
        } catch (DuplicateEmailException e) {
            model.addAttribute("error", "중복된 이메일입니다. 다른 이메일을 사용해주세요."); // 에러 메시지 전달
            return "signup"; // 회원가입 페이지 다시 보여주기
        }
    }

    @GetMapping("/")
    public String index() {
        return "index"; // index.html 또는 index.jsp 페이지로 리턴
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // 403.html 또는 403.jsp 페이지로 리턴
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

}
