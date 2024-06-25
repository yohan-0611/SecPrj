package com.avi6.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@AllArgsConstructor
@Data
public class TPageRequestDTO {

    private int page; // 페이지 번호

    private int size; // 페이지당 목록 수

    private String type; // 조건 검색의 타입(제목, 내용, 작성자)

    private String keyword; // 키워드

    public TPageRequestDTO() {
        // 기본값으로 각 값 설정
        this.page = 1;
        this.size = 10;
    }

    // 나중에 Page 처리할 Pageable 리턴 메서드 정의. 정렬 객체를 파라미터로 받아서 처리
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort); // 페이지는 0부터 시작하기 때문에 초기값 -1 로 리턴
    }
    
    // 기본 정렬을 제공하는 메서드 추가
    public Pageable toPageable() {
        return getPageable(Sort.by("bno").descending()); // 기본으로 bno를 기준으로 내림차순 정렬
    }
}
