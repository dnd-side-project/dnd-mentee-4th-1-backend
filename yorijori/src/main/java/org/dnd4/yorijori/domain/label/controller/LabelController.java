package org.dnd4.yorijori.domain.label.controller;

import java.security.Principal;

import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.common.ResultList;
import org.dnd4.yorijori.domain.label.service.LabelService;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.service.RecipeService;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.dnd4.yorijori.domain.user_follow.service.UserFollowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class LabelController {

	private final RecipeService recipeService;
	private final LabelService labelService;

	@PostMapping("/recipes/{id}/label")
	public Result<Boolean> add(@PathVariable Long id, Principal principal) {
		Recipe recipe = recipeService.get(id);
		recipeService.incWishCount(recipe);
		User user = (User) ((Authentication) principal).getPrincipal();
		labelService.add(user, recipe);
		return new Result<Boolean>(true);
	}

	@DeleteMapping("/recipes/{id}/label")
	public Result<Boolean> delete(@PathVariable Long id, Principal principal) {
		Recipe recipe = recipeService.get(id);
		recipeService.decWishCount(recipe);
		User user = (User) ((Authentication) principal).getPrincipal();
		labelService.delete(user, recipe);
		return new Result<Boolean>(true);
	}

	private final UserRepository userRepository;

	@GetMapping("/user/{userId}/label")
	@PreAuthorize("#userId==principal.id")
	public ResultList<ResponseDto> labelList(@PathVariable Long userId,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
		return new ResultList<ResponseDto>(labelService.labelList(user, limit, offset));
	}

}
