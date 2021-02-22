package org.dnd4.yorijori.domain.label.controller;

import java.security.Principal;

import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.label.entity.Label;
import org.dnd4.yorijori.domain.label.service.LabelService;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.service.RecipeService;
import org.dnd4.yorijori.domain.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
public class LabelController {

	private final RecipeService recipeService;
	private final LabelService labelService;

	@PostMapping("/{id}/label")
	public Result<Boolean> add(@PathVariable Long id, Principal principal) {
		Recipe recipe = recipeService.get(id);
		User user = (User) ((Authentication) principal).getPrincipal();
		labelService.add(user, recipe);
		return new Result<Boolean>(true);
	}

	@DeleteMapping("/{id}/label")
	public Result<Boolean> delete(@PathVariable Long id, Principal principal) {
		Recipe recipe = recipeService.get(id);
		User user = (User) ((Authentication) principal).getPrincipal();
		labelService.delete(user, recipe);
		return new Result<Boolean>(true);
	}

}
