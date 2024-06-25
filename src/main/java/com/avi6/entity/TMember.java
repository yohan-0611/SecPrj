package com.avi6.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "t_member")
public class TMember extends BaseEntity { // 회원 Entity
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memId;
	
    private String email;
	
    private String password;
	
    private String nickname;
	
    private String wishList;
}
