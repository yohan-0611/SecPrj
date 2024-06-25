package com.avi6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.avi6.dto.UserDTO;
import com.avi6.entity.UserEntity;
import com.avi6.repository.UserRepository;
import com.avi6.user.DuplicateEmailException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(UserDTO userDTO) throws DuplicateEmailException {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateEmailException("중복된 이메일입니다.");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        // 다른 속성도 설정

        userRepository.save(userEntity);
    }
}
