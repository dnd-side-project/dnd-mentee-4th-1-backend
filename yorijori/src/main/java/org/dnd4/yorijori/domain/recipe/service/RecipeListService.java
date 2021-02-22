package org.dnd4.yorijori.domain.recipe.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.dnd4.yorijori.domain.label.repository.LabelRepository;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.repository.RecipeDslRepository;
import org.dnd4.yorijori.domain.recipe.repository.RecipeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RecipeListService {
	
	private final RecipeDslRepository recipeDslRepository;
	
	public List<ResponseDto> findAll(String step, String time, LocalDateTime start, LocalDateTime end, String order, String keyword, int limit, int offset) {
		return recipeDslRepository.findAll(step, time, start, end, order, keyword, limit, offset).stream()
				.map(ResponseDto::new).collect(Collectors.toList());
	}
	
}
