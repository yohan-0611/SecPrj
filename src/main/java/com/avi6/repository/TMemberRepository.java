package com.avi6.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avi6.entity.TMember;

public interface TMemberRepository extends JpaRepository<TMember, Long> {

}
