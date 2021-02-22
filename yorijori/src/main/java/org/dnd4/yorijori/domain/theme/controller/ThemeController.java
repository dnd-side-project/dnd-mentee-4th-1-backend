package org.dnd4.yorijori.domain.theme.controller;

import org.dnd4.yorijori.domain.common.ResultList;
import org.dnd4.yorijori.domain.theme.dto.ThemeDto;
import org.dnd4.yorijori.domain.theme.service.ThemeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ThemeController {
	
	private final ThemeService themeService;
	
	@GetMapping("/theme")
	public ResultList<ThemeDto> getAllTheme(){
		return new ResultList<ThemeDto>(themeService.getAllTheme());
	}
}
