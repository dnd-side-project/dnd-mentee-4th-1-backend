package org.dnd4.yorijori.domain.recipe.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.repository.RecipeDslRepository;
import org.dnd4.yorijori.domain.user.entity.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecipeListService {
	
	private final RecipeDslRepository recipeDslRepository;
	
	public List<ResponseDto> findAll(String stepStart, String stepEnd, String time, LocalDateTime start, LocalDateTime end, String order, String keyword, int limit, int offset) {
		return recipeDslRepository.findAll(stepStart, stepEnd, time, start, end, order, keyword, limit, offset).stream()
				.map(ResponseDto::new).collect(Collectors.toList());
	}
	
	public List<ResponseDto> getUserRecipes(User user, int limit, int offset) {
		return recipeDslRepository.getUserRecipes(user, limit, offset).stream()
				.map(ResponseDto::new).collect(Collectors.toList());
	}
	
}
