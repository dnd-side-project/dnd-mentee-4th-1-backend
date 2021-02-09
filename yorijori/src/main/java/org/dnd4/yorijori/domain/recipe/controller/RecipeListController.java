package org.dnd4.yorijori.domain.recipe.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.service.RecipeListService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class RecipeListController {

	private final RecipeListService recipeListService;

	@GetMapping("/recipe")
	public List<ResponseDto> findById(@RequestParam(required = false) String id,
			@RequestParam(required = false) String step, @RequestParam(required = false) String time,
			@RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate,
			@RequestParam(required = false) String order, @RequestParam(required = false) String keyword,
			@RequestParam(required = false, defaultValue = "10") int limit,
			@RequestParam(required = false, defaultValue = "0") int offset) {
		LocalDateTime start = null;
		LocalDateTime end = null;
		if (startDate != null) {
			start = LocalDateTime.parse(startDate + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		if (endDate != null) {
			end = LocalDateTime.parse(endDate + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return recipeListService.findAll(id, step, time, start, end, order, keyword, limit, offset);
	}

}
