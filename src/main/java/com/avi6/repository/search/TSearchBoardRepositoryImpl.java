package com.avi6.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;


import com.avi6.entity.QTBoard;
import com.avi6.entity.QTBoardImage;
import com.avi6.entity.QTMember;
import com.avi6.entity.QTReply;
import com.avi6.entity.TBoard;
import com.avi6.repository.TSearchBoardRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TSearchBoardRepositoryImpl extends QuerydslRepositorySupport implements TSearchBoardRepository{

   public TSearchBoardRepositoryImpl() {
      super(TBoard.class);
   
   }

   @Override
   public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
      QTBoard tBoard = QTBoard.tBoard;
      QTMember tMember = QTMember.tMember;
      QTBoardImage tBoardImage = QTBoardImage.tBoardImage; // 이미지 추가
      //QTReply tReply = QTReply.tReply; 

      
      JPQLQuery<TBoard> jpqlQuery = from(tBoard);
      jpqlQuery.leftJoin(tMember).on(tBoard.member.eq(tMember));
      jpqlQuery.leftJoin(tBoard.imageList, tBoardImage); // 이미지 조인 추가
      
      JPQLQuery<Tuple> tuples = jpqlQuery.select(tBoard, tBoardImage, tMember);
      
      BooleanBuilder booleanBuilder = new BooleanBuilder();
      booleanBuilder.and(tBoard.bno.gt(0L));
      //BooleanExpression exBooleanExpression = tBoard.bno.gt(0L);//bno > 0 이상인 조건 결정
      //tuples.groupBy(tBoard);
      
      //키워드 조건 추가
            //booleanBuilder.and(exBooleanExpression);
      
            
            //키워드 타입은 one or all 일수도 있음.
            if(type != null) {
               //키워드 분리
               String[] typeArr = type.split("");
               
               //검색조건 추가
               BooleanBuilder cBuilder = new BooleanBuilder();
               
               for(String s : typeArr) {
                  switch (s) {
                  
                  case "t"://제목 검색
                     cBuilder.or(tBoard.title.contains(keyword));
                     break;
                     
                  case "c"://내용 검색
                     cBuilder.or(tBoard.content.contains(keyword));
                     break;
                     
                  case "w"://작성자 검색
                     cBuilder.or(tMember.nickname.contains(keyword));
                     break;
         
                  }
               }
               //조건검색 처리 완료
               booleanBuilder.and(cBuilder);
            }
            
            //위 join 에서 생성된 tuple 의 조건에 booleanBuilder add
            //이후에 group by 처리
            tuples.where(booleanBuilder);
            tuples.groupBy(tBoard);
            
            
            JPQLQuery<Tuple> paginatedQuery = this.getQuerydsl().applyPagination(pageable, tuples);
             List<Tuple> result = paginatedQuery.fetch();
             long count = paginatedQuery.fetchCount();

             // 객체 배열로 변환하여 PageImpl 객체로 반환
             List<Object[]> resultList = result.stream()
                     .map(tuple -> new Object[]{tuple.get(tBoard), tuple.get(tBoardImage), tuple.get(tMember)})
                     .collect(Collectors.toList());

             return new PageImpl<>(resultList, pageable, count);
            
      
   }

}