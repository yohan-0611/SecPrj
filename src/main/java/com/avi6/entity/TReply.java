package com.avi6.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Table(name = "t_reply")
public class TReply extends BaseEntity{//댓글 Entity
   
   @Id
   @GeneratedValue(strategy =  GenerationType.IDENTITY)
   private Long reviewNum;
   
   private String text;//리뷰 내용
  
   @ManyToOne(fetch = FetchType.LAZY) //1:N 관계로 테이블을 생성
   @ToString.Exclude
   private TBoard board;
   
   @ManyToOne(fetch = FetchType.LAZY)
   private TMember member;
   
   public void setText(String text) {
      this.text = text;
   }
}