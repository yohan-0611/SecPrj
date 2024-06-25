package com.avi6.entity;


import jakarta.persistence.Column;
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
public class UserEntity {
   
    @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;

       private String username;
       
       @Column(nullable = false, unique = true)
       private String email;

       @Column(nullable = false)
       private String password;

       // Getters and setters
       public Long getId() {
           return id;
       }

       public void setId(Long id) {
           this.id = id;
       }

       public String getUsername() {
           return username;
       }

       public void setUsername(String username) {
           this.username = username;
       }

       public String getPassword() {
           return password;
       }

       public void setPassword(String password) {
           this.password = password;
       }
       
       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

}