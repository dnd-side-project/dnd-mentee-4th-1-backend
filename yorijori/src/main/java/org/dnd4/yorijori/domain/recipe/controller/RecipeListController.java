package org.dnd4.yorijori.domain.recipe.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.dnd4.yorijori.domain.common.ResultList;
import org.dnd4.yorijori.domain.monthly_label.service.MonthlyLabelService;
import org.dnd4.yorijori.domain.monthly_view.service.MonthlyViewService;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.service.RecipeListService;
import org.dnd4.yorijori.domain.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RecipeListController {

	private final RecipeListService recipeListService;
	private final MonthlyViewService monthlyViewService;
	private final MonthlyLabelService monthlyLabelService;

	@GetMapping("/recipes")
	public ResultList<ResponseDto> recipeList(@RequestParam(required = false) String queryType,
			@RequestParam(required = false) String stepStart, @RequestParam(required = false) String stepEnd,
			@RequestParam(required = false) String time, @RequestParam(required = false) String startDate,
			@RequestParam(required = false) String endDate, @RequestParam(required = false) String order,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset, Principal principal) {
		LocalDateTime start = null;
		LocalDateTime end = null;
		if (startDate != null) {
			start = LocalDateTime.parse(startDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		if (endDate != null) {
			end = LocalDateTime.parse(endDate + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		if (queryType.equals("search")) {
			return new ResultList<ResponseDto>(recipeListService.findAll(stepStart, stepEnd, time, start, end, order, keyword, limit, offset));
		}
		if (queryType.equals("viewTop")) {
			return new ResultList<ResponseDto>(monthlyViewService.rank(limit));
		}
		if (queryType.equals("labelTop")) {
			return new ResultList<ResponseDto>(monthlyLabelService.rank(limit));
		}
		if (queryType.equals("my")) {
			User user = (User) ((Authentication) principal).getPrincipal();
			return new ResultList<ResponseDto>(recipeListService.getUserRecipes(user, limit, offset));
		}
		return null;
	}

}
