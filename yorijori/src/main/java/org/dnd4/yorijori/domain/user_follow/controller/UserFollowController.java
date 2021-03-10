package org.dnd4.yorijori.domain.user_follow.controller;

import java.security.Principal;
import java.util.List;

import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.common.ResultList;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user_follow.dto.BooleanDto;
import org.dnd4.yorijori.domain.user_follow.service.UserFollowService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserFollowController {

	private final UserFollowService userFollowService;
	
	@GetMapping("/followers")
	public ResultList<UserDto> followerList(Principal principal,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		User user = (User) ((Authentication) principal).getPrincipal();
		return new ResultList<UserDto> (userFollowService.followerList(user, limit, offset));
	}

	@GetMapping("/followings")
	public ResultList<UserDto> followingList(Principal principal,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		User user = (User) ((Authentication) principal).getPrincipal();
		return new ResultList<UserDto> (userFollowService.followingList(user, limit, offset));
	}

	@PostMapping("/follow/{followingId}")
	public Result<Boolean> follow(@PathVariable Long followingId, Principal principal) {
		User follower = (User) ((Authentication) principal).getPrincipal();
		userFollowService.follow(followingId, follower);
		return new Result<Boolean>(true);
	}

	@DeleteMapping("/follow/{followingId}")
	public Result<Boolean> unfollow(@PathVariable Long followingId, Principal principal) {
		User follower = (User) ((Authentication) principal).getPrincipal();
		userFollowService.unfollow(followingId, follower);
		return new Result<Boolean>(true);
	}
	
	@PutMapping("/following/{followingId}/alarm")
	public Result<Boolean> followingAlarm(Principal principal, @PathVariable Long followingId, @RequestBody BooleanDto booleanDto) {
		User user = (User) ((Authentication) principal).getPrincipal();
		userFollowService.followingAlarm(user, followingId, booleanDto.getIsOn());
		return new Result<Boolean>(true);
	}
	
	@PutMapping("/follower/{followerId}/alarm")
	public Result<Boolean> followerAlarm(Principal principal, @PathVariable Long followerId, @RequestBody BooleanDto booleanDto) {
		User user = (User) ((Authentication) principal).getPrincipal();
		userFollowService.followerAlarm(user, followerId, booleanDto.getIsOn());
		return new Result<Boolean>(true);
	}
	
	@GetMapping("/followerFeeds")
	public ResultList<ResponseDto> followerFeed(Principal principal,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		User user = (User) ((Authentication) principal).getPrincipal();
		List<ResponseDto> result = userFollowService.followerFeed(user, limit, offset);
		return new ResultList<ResponseDto>(result);
	}

	@GetMapping("/followingFeeds")
	public ResultList<ResponseDto> followingFeed(Principal principal,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		User user = (User) ((Authentication) principal).getPrincipal();
		List<ResponseDto> result = userFollowService.followingFeed(user, limit, offset);
		return new ResultList<ResponseDto>(result);
	}

}
