package com.avi6.dto;

import java.time.LocalDateTime;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data


public class UserDTO {
    private String username;
      private String password;       
      private String email;

       // Getters and setters
       public String getUsername() {
           return username;
       }

       public void setUsername(String username) {
           this.username = username;
       }
       
       public String getEmail() {
           return email;
       }

       public void setEmail(String email) {
           this.email = email;
       }

       public String getPassword() {
           return password;
       }

       public void setPassword(String password) {
           this.password = password;
       }
}