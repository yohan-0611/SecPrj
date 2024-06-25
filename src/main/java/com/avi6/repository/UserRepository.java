package com.avi6.repository;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.avi6.entity.UserEntity;


public interface UserRepository extends JpaRepository<UserEntity, Long>{
	
	//사용자 인증 이메일을 찾는 메서드 정의
	Optional<UserEntity> findByEmail(String email);

	
	//중복이메일 검증 로직
	boolean existsByEmail(String email);


}
