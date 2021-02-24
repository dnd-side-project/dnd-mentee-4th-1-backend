package org.dnd4.yorijori.domain.user.controller;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

import org.dnd4.yorijori.Security.JwtTokenProvider;
import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.dnd4.yorijori.domain.user.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/users/me")
    public Result<UserDto> get(Principal principal){
        User user = (User) ((Authentication) principal).getPrincipal();

        return new Result<UserDto>(new UserDto(user));
    }

    @GetMapping("/users/{id}")
    public Result<UserDto> get(@PathVariable Long id){

        User user = userService.get(id);

        return new Result<UserDto>(new UserDto(user));
    }

    @PostMapping("/join")
    public Result<Long> join(@RequestBody Map<String, String> user) {

        Long userId = userService.join(user.get("name"), user.get("email"), user.get("imageUrl"),"ROLE_USER");

        return new Result<Long>(userId);
    }

    @PostMapping("/login")
    public Result<String> login(@RequestBody Map<String, String> user) {

        return new Result<String>(userService.makeJwtByEmail(user.get("email")));
    }
}
