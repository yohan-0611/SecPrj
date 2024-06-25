package com.avi6.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.avi6.dto.TBoardDTO;
import com.avi6.dto.TBoardImageDTO;
import com.avi6.dto.UploadResDTO;

import net.coobird.thumbnailator.Thumbnailator;

@RestController
public class TUploadController {

    @Value("${com.avi6.path}")
    private String uploadPath;
    
    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(@RequestParam("fileName") String fileName){
        ResponseEntity<byte[]> responseEntity = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            File file = new File(uploadPath + File.separator + srcFileName);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));

            responseEntity = new ResponseEntity<>(Files.readAllBytes(file.toPath()), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return responseEntity;
    }
    
    @PostMapping("/uploadAjax")
    public ResponseEntity<List<UploadResDTO>> uploadFile(@RequestParam("uploadFiles") MultipartFile[] multipartFiles) {
        List<UploadResDTO> resDTOList = new ArrayList<>();
        List<TBoardImageDTO> imageDTOList = new ArrayList<>(); // TBoardImageDTO 리스트
        
        for(MultipartFile file : multipartFiles) {
            String oriName = file.getOriginalFilename();
            String fileName = oriName.substring(oriName.indexOf("\\") + 1);
        
            if(!file.getContentType().startsWith("image")) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            
            String folderPath = mkFolder();
            String uuid = UUID.randomUUID().toString();
            String savedName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" +fileName;
            Path savePath = Paths.get(savedName);
            
            try {
                file.transferTo(savePath);
                
                String thumbImgName = uploadPath + File.separator + folderPath + File.separator + "thum" + uuid + "_" +fileName;
                File thumFile = new File(thumbImgName);
                Thumbnailator.createThumbnail(savePath.toFile(), thumFile, 100, 100);
                
                resDTOList.add(new UploadResDTO(fileName, uuid, folderPath));
                
                // TBoardImageDTO 객체 생성 및 리스트에 추가
                TBoardImageDTO imageDTO = TBoardImageDTO.builder()
                        .uuid(uuid)
                        .imgName(fileName)
                        .path(folderPath)
                        .build();
                imageDTOList.add(imageDTO);
                
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        
        // 여기서 TBoardDTO에 이미지 리스트 설정 (예제용으로 새 객체 생성)
        TBoardDTO boardDTO = TBoardDTO.builder()
                .title("Sample Title")
                .content("Sample Content")
                .imageDTOList(imageDTOList) // 이미지 리스트 설정
                .build();
        
        // 클라이언트에게 전달할 데이터를 설정
        return new ResponseEntity<>(resDTOList, HttpStatus.OK);
    }
    
    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> delFile(@RequestParam("fileName") String fileName){
        try {
            String targetFile = URLDecoder.decode(fileName, "UTF-8");
            
            File file = new File(uploadPath + File.separator + targetFile);
            file.delete();
            
            return new ResponseEntity<>(true, HttpStatus.OK);
            
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    private String mkFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        
        File uploadPathFolder = new File(uploadPath, str);
        if(!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return str;
    }
}
