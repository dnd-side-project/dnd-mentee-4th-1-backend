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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserFollowService {
	
	private final UserFollowRepository userFollowRepository;
	private final UserFollowDslRepository userFollowDslRepository;
	private final UserRepository userRepository;

	public List<UserDto> followingList(User user) {
		List<UserDto> result = userFollowDslRepository.findFollowingByFollower(user, 10, 0).stream()
				.map(UserDto::new).collect(Collectors.toList());
		return result;
	}
	
	public List<UserDto> followerList(User user) {
		List<UserDto> result = userFollowDslRepository.findFollowerByFollowing(user, 10, 0).stream()
				.map(UserDto::new).collect(Collectors.toList());
		return result;
	}
	
	@Transactional
    public void follow(Long followingId, Long followerId){
		User follower = userRepository.findById(followerId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followerId));
		User following = userRepository.findById(followingId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followingId));
		UserFollow entity = UserFollow.builder().follower(follower).following(following).build();
		userFollowRepository.save(entity);
    }
	
	@Transactional
    public void unfollow(Long followingId, Long followerId){
		User follower = userRepository.findById(followerId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followerId));
		User following = userRepository.findById(followingId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followingId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		userFollowRepository.delete(entity);
    }

	@Transactional
	public void followingAlarmOn(Long userId, Long followingId) {
		User follower = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		User following = userRepository.findById(followingId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followingId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		entity.followingAlarmOn();
	}
	@Transactional
	public void followingAlarmOff(Long userId, Long followingId) {
		User follower = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		User following = userRepository.findById(followingId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followingId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		entity.followingAlarmOff();
	}
	@Transactional
	public void followerAlarmOn(Long userId, Long followerId) {
		User follower = userRepository.findById(followerId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followerId));
		User following = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		entity.followerAlarmOn();
	}
	@Transactional
	public void followerAlarmOff(Long userId, Long followerId) {
		User follower = userRepository.findById(followerId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + followerId));
		User following = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		UserFollow entity = userFollowRepository.findByFollowerAndFollowing(follower, following).orElseThrow(()->new IllegalArgumentException("데이터가 없습니다."));
		entity.followerAlarmOff();
	}
	
	public List<ResponseDto> followerFeed(Long userId, int limit, int offset) {
		List<ResponseDto> result = userFollowDslRepository.followerFeed(userId, limit, offset).stream()
				.map(ResponseDto::new).collect(Collectors.toList());
		return result;
	}

	public List<ResponseDto> followingFeed(Long userId, int limit, int offset) {
		List<ResponseDto> result = userFollowDslRepository.followingFeed(userId, limit, offset).stream()
				.map(ResponseDto::new).collect(Collectors.toList());
		return result;
	}
}
