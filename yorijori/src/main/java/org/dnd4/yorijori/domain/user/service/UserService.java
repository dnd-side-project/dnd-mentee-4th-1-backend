package org.dnd4.yorijori.domain.user.service;

import org.dnd4.yorijori.Security.JwtTokenProvider;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.dnd4.yorijori.domain.user_follow.entity.UserFollow;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Transactional
    public void imageUpdate(User user, String imageUrl){
		user.setImageUrl(imageUrl);
		userRepository.save(user);
    }
	
}
