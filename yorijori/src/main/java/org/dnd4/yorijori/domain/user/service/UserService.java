package org.dnd4.yorijori.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.Security.JwtTokenProvider;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public User get(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + id));

        return user;
    }

    @Transactional
    public Long join(String name, String email, String imageUrl, String role){
        return userRepository.save(User.builder()
                .name(name)
                .email(email)
                .imageUrl(imageUrl)
                .roles(Collections.singletonList(role))
                .build()).getId();
    }

    @Transactional
    public String makeJwtByEmail(String email){
        User member = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));

        return jwtTokenProvider.createToken(member);

    }
}
