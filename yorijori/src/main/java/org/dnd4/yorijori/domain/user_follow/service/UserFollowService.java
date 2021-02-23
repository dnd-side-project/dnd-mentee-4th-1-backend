package org.dnd4.yorijori.domain.user_follow.service;

import java.util.List;
import java.util.stream.Collectors;

import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.dnd4.yorijori.domain.user_follow.entity.UserFollow;
import org.dnd4.yorijori.domain.user_follow.repository.UserFollowDslRepository;
import org.dnd4.yorijori.domain.user_follow.repository.UserFollowRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserFollowService {
	
	private final UserFollowRepository userFollowRepository;
	private final UserFollowDslRepository userFollowDslRepository;
	private final UserRepository userRepository;

	public List<UserDto> followingList(User user, int limit, int offset) {
		List<UserDto> result = userFollowDslRepository.findFollowingByFollower(user, limit, offset).stream()
				.map(UserDto::new).collect(Collectors.toList());
		return result;
	}
	
	public List<UserDto> followerList(User user, int limit, int offset) {
		List<UserDto> result = userFollowDslRepository.findFollowerByFollowing(user, limit, offset).stream()
				.map(UserDto::new).collect(Collectors.toList());
		return result;
	}
	
	@Transactional
    public void follow(Long followingId, User follower){
		User following = userRepository.findById(followingId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followingId));
		UserFollow entity = UserFollow.builder().follower(follower).following(following).build();
		userFollowRepository.save(entity);
    }
	
	@Transactional
    public void unfollow(Long followingId, User follower){
		User following = userRepository.findById(followingId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followingId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		userFollowRepository.delete(entity);
    }

	@Transactional
	public void followingAlarm(User follower, Long followingId, Boolean on) {
		User following = userRepository.findById(followingId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followingId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		if(on) entity.followingAlarmOn();
		else entity.followingAlarmOff();
	}

	@Transactional
	public void followerAlarm(User following, Long followerId, Boolean on) {
		User follower = userRepository.findById(followerId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followerId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		if(on) entity.followerAlarmOn();
		else entity.followerAlarmOff();
	}
	
	public List<ResponseDto> followerFeed(User user, int limit, int offset) {
		List<ResponseDto> result = userFollowDslRepository.followerFeed(user, limit, offset).stream()
				.map(ResponseDto::new).collect(Collectors.toList());
		return result;
	}

	public List<ResponseDto> followingFeed(User user, int limit, int offset) {
		List<ResponseDto> result = userFollowDslRepository.followingFeed(user, limit, offset).stream()
				.map(ResponseDto::new).collect(Collectors.toList());
		return result;
	}
}
