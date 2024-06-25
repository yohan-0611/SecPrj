package com.avi6.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.avi6.dto.TBoardDTO;
import com.avi6.dto.TBoardImageDTO;
import com.avi6.dto.TPageRequestDTO;
import com.avi6.entity.TBoard;
import com.avi6.entity.TBoardImage;
import com.avi6.entity.TMember;
import com.avi6.repository.TBoardImageRepository;
import com.avi6.service.TBoardService;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnailator;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class TRegisterController {

    private final TBoardService tBoardService;
    private final TBoardImageRepository tBoardImageRepository;

    @Value("${com.avi6.path}")
    private String uploadPath;

    @GetMapping("/index")
    public String list0() {
        return "index";
    }

    @GetMapping("/board")
    public String list2(TPageRequestDTO tpageRequestDTO, Model model) {
      model.addAttribute("result", tBoardService.getList(tpageRequestDTO));
        return "board";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(TBoardDTO tBoardDTO, @RequestParam("uploadFiles") MultipartFile[] uploadFiles, RedirectAttributes redirectAttributes) {
        // Check if memId is null or empty
        if (tBoardDTO.getMemId() == null) {
            redirectAttributes.addFlashAttribute("msg", "회원 ID를 입력하세요.");
            return "redirect:/register";
        }

        // Fetch the member from the database
        TMember member = tBoardService.findMemberById(tBoardDTO.getMemId());
        if (member == null) {
            redirectAttributes.addFlashAttribute("msg", "회원 번호가 일치하지 않습니다.");
            return "redirect:/register";
        }

        // 게시판 등록
        Long bno = tBoardService.register(tBoardDTO);

        if (bno < 0) {
            System.out.println("게시판 등록 예외 발생");
            return "redirect:/register";
        }

        // 게시글 엔티티 생성
        TBoard tBoard = TBoard.builder()
                .bno(bno)
                .title(tBoardDTO.getTitle())
                .content(tBoardDTO.getContent())
                .member(member) // TMember 설정
                .build();

        // 이미지 파일 저장
        List<TBoardImage> imageList = new ArrayList<>();
        for (MultipartFile file : uploadFiles) {
            if (!file.isEmpty() && file.getContentType().startsWith("image")) {
                try {
                    // 저장할 경로 설정
                    String folderPath = mkFolder();
                    String uuid = UUID.randomUUID().toString();
                    String saveName = uuid + "_" + file.getOriginalFilename();
                    Path savePath = Paths.get(uploadPath, folderPath, saveName);

                    // 파일 저장
                    file.transferTo(savePath);

                    // 썸네일 생성
                    String thumbImgName = uploadPath + File.separator + folderPath + File.separator + "thum" + uuid + "_" + file.getOriginalFilename();
                    File thumFile = new File(thumbImgName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumFile, 100, 100);

                    // DB에 저장
                    TBoardImage tBoardImage = TBoardImage.builder()
                            .imgName(file.getOriginalFilename())
                            .path(folderPath) // path 설정
                            .uuid(uuid)
                            .tBoard(tBoard)
                            .build();
                    TBoardImage savedImage = tBoardImageRepository.save(tBoardImage);

                    imageList.add(savedImage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 이미지 리스트를 DTO로 변환
        List<TBoardImageDTO> imageDTOList = imageList.stream()
                .map(image -> TBoardImageDTO.builder()
                        .inum(image.getInum())
                        .uuid(image.getUuid())
                        .imgName(image.getImgName())
                        .path(image.getPath()) // path 설정
                        .build())
                .collect(Collectors.toList());

        // DTO에 이미지 리스트 설정
        tBoardDTO.setImageDTOList(imageDTOList);

        redirectAttributes.addFlashAttribute("msg", "게시판 잘 등록되었음... 등록 번호 : " + bno);

        return "redirect:/board/list";
    }

    private String mkFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        File uploadPathFolder = new File(uploadPath, str);
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return str;
    }

    
}
