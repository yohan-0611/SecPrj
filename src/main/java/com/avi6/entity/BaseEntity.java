package com.avi6.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

/*
 * Base Entity 는 추상클래스로 선언하세요.
 */
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})//이렇게 선언하면,main 클래스에 @enable을 반드시 선언해야함 메인클래스는 보더어플리케이션
@Getter
public abstract class BaseEntity {
	
	//회원가입일등이나, 작성일, 수정일 등을 자동관리하도록 필드선언합니다.
	
	@CreatedDate
	@Column(name = "regdate", updatable = false)//수정금지, 컬럼명은 regdate로 설정
	private LocalDateTime regDate;
	
	@LastModifiedDate
	@Column(name = "moddate")
	private LocalDateTime modDate;

}
