package com.avi6.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.avi6.dto.TBoardDTO;
import com.avi6.dto.TBoardImageDTO;
import com.avi6.dto.TPageRequestDTO;
import com.avi6.dto.TPageResultDTO;
import com.avi6.entity.TBoard;
import com.avi6.entity.TBoardImage;
import com.avi6.entity.TMember;
import com.avi6.entity.TReply;
import com.avi6.repository.TBoardImageRepository;
import com.avi6.repository.TBoardRepository;
import com.avi6.repository.TMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TBoardServiceImpl implements TBoardService {

    private final TBoardRepository tBoardRepository;
    
    private final TBoardImageRepository tBoardImageRepository;
    
    private final TMemberRepository tMemberRepository;

    @Transactional
    @Override
    public Long register(TBoardDTO tBoardDTO) {
        // Check if memId is null
        if (tBoardDTO.getMemId() == null) {
            throw new IllegalArgumentException("회원 ID를 입력하세요.");
        }

        TMember member = tMemberRepository.findById(tBoardDTO.getMemId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID: " + tBoardDTO.getMemId()));

        TBoard tBoard = TBoard.builder()
                .title(tBoardDTO.getTitle())
                .content(tBoardDTO.getContent())
                .member(member) // TMember 설정
                .build();

        tBoardRepository.save(tBoard);

        return tBoard.getBno();
    }

    public TMember findMemberById(Long memId) {
        return tMemberRepository.findById(memId)
                .orElse(null);
    }

    @Override
    public TPageResultDTO<TBoardDTO, Object[]> getList(TPageRequestDTO tPageRequestDto) {
        // entity -> dto 변환 메서드 Function 객체 생성
        Function<Object[], TBoardDTO> convertFunction = (entity -> entityToDTO(
                (TBoard) entity[0],
                Arrays.asList((TBoardImage) entity[1]),
                (TMember) entity[2],
                ((TBoard) entity[0]).getVisitCount(),
                entity.length > 3 ? ((Number) entity[3]).intValue() : 0));

        // 정렬 조건 설정
        Sort sort = Sort.by("bno").descending();
        // 페이징 처리
        Pageable pageable = tPageRequestDto.getPageable(sort);

        // 검색 조건에 따른 페이지 결과 조회
        Page<Object[]> result;
        if (tPageRequestDto.getType() != null && tPageRequestDto.getKeyword() != null) {
            result = tBoardRepository.searchPage(tPageRequestDto.getType(), tPageRequestDto.getKeyword(), pageable);
        } else {
            result = tBoardRepository.getListPage(pageable);
        }

        return new TPageResultDTO<>(result, convertFunction); 
    }


    @Override
    public TBoardDTO getBoard(Long bno) {
        List<Object[]> result = tBoardRepository.getBoardWithAll(bno);

        if (result == null || result.isEmpty()) {
            throw new IllegalArgumentException("Invalid board Id:" + bno);
        }

        TBoard tBoard = (TBoard) result.get(0)[0];
        List<TBoardImage> tBoardImages = result.stream()
                .filter(objects -> objects[1] != null)
                .map(objects -> (TBoardImage) objects[1])
                .collect(Collectors.toList());
        TMember member = (TMember) result.get(0)[2];
        int visitCount = (int) result.get(0)[3];

        if (member == null) {
            throw new IllegalArgumentException("Member not found for board Id:" + bno);
        }

        // 댓글 수를 가져오는 로직 추가
        int replyCount = tBoardRepository.getReplyCountByBoardId(bno);
        System.out.println("Board ID: " + bno + ", Reply Count: " + replyCount);

        return entityToDTO(tBoard, tBoardImages, member, visitCount, replyCount);
    }

    
    // DTO를 엔티티로 변환하는 메서드
    public Map<String, Object> dtoToEntity(TBoardDTO tBoardDTO) {
        TMember member = TMember.builder()
                .memId(tBoardDTO.getMemId())
                .nickname(tBoardDTO.getNickname())
                .build();

        TBoard tBoard = TBoard.builder()
                .bno(tBoardDTO.getBno())
                .title(tBoardDTO.getTitle())
                .content(tBoardDTO.getContent())
                .visitCount(tBoardDTO.getVisitCount())
                .member(member)
                .build();

        List<TBoardImage> imageList = new ArrayList<>();
        if (tBoardDTO.getImageDTOList() != null) {
            imageList = tBoardDTO.getImageDTOList().stream()
                    .filter(imageDTO -> imageDTO != null)  // null 체크 추가
                    .map(imageDTO -> TBoardImage.builder()
                            .uuid(imageDTO.getUuid())
                            .imgName(imageDTO.getImgName())
                            .path(imageDTO.getPath())
                            .tBoard(tBoard)  // 연관 관계 설정
                            .build())
                    .collect(Collectors.toList());
        }

        return Map.of(
                "tBoard", tBoard,
                "imageList", imageList
        );
    }

    // 엔티티를 DTO로 변환하는 메서드
    private TBoardDTO entityToDTO(TBoard tBoard, List<TBoardImage> tBoardImages, TMember member, int visitCount, int replyCount) {
        List<TBoardImageDTO> imageDTOList = tBoardImages.stream()
                .filter(image -> image != null) // null 체크
                .map(image -> TBoardImageDTO.builder()
                        .uuid(image.getUuid())
                        .imgName(image.getImgName())
                        .path(image.getPath())
                        .build())
                .collect(Collectors.toList());

        return TBoardDTO.builder()
                .bno(tBoard.getBno())
                .title(tBoard.getTitle())
                .content(tBoard.getContent())
                .visitCount(visitCount)
                .nickname(member.getNickname())
                .memId(member.getMemId())
                .imageDTOList(imageDTOList)
                .regDate(tBoard.getRegDate())
                .modDate(tBoard.getModDate())
                .replyCount(replyCount) // 댓글 수 추가
                .build();
    }

    
    //조회수 증가시키는 메서드
	@Override
	public void incrementVisitCount(Long bno) {
		Optional<TBoard> tBoardOpt = tBoardRepository.findById(bno);
        if (tBoardOpt.isPresent()) {
            TBoard tBoard = tBoardOpt.get();
            tBoard.setVisitCount(tBoard.getVisitCount() + 1);
            tBoardRepository.save(tBoard);
        }
	}

	@Override
	@Transactional
	public void updateArticle(TBoardDTO tBoardDTO) {

		TBoard tBoard = tBoardRepository.getReferenceById(tBoardDTO.getBno());
		
		tBoard.setTitle(tBoardDTO.getTitle());
		tBoard.setContent(tBoardDTO.getContent());
		
		tBoardRepository.save(tBoard);
	}

	@Override
	public TBoardDTO get(Long bno) {
		System.out.println("글 상세 요청함 글 번호 --> " + bno);
		
		Object obj = tBoardRepository.getBoardWithBno(bno);
		
		Object[] arr = (Object[])obj;
		
		return entityToDTO((TBoard)arr[0], null, (TMember)arr[1], (Long)arr[2] ,(Long)arr[3]);
	}

	@Override
	public void delArticle(Long bno) {
		
		//댓글부터 삭제
		tBoardRepository.delByBno(bno);
		
		//글 삭제
		tBoardRepository.deleteById(bno);
	}

}
