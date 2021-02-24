package org.dnd4.yorijori.domain.user.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.dnd4.yorijori.Security.JwtTokenProvider;
import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.dnd4.yorijori.domain.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/join")
    public Result<Long> join(@RequestBody Map<String, String> user) {
        return new Result<Long>(userRepository.save(User.builder()
        		.name(user.get("name"))
                .email(user.get("email"))
                .imageUrl(user.get("imageUrl"))
                .roles(Collections.singletonList("ROLE_USER"))
                .build()).getId());
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> user) {
        User member = userRepository.findByEmail(user.get("email"))
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 E-MAIL 입니다."));
        return new Result<String>(jwtTokenProvider.createToken(member));
    }
    
    @PutMapping("/user/image")
    public Result<Boolean> imageUpdate(@RequestBody Map<String, String> imageUrl, Principal principal) {
    	User user = (User) ((Authentication) principal).getPrincipal();
		userService.imageUpdate(user, imageUrl.get("imageUrl"));
		return new Result<Boolean>(true);
    }
    
}
