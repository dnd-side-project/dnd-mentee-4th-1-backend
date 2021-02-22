package org.dnd4.yorijori.domain.theme.service;

import java.util.List;
import java.util.stream.Collectors;

import org.dnd4.yorijori.domain.theme.dto.ThemeDto;
import org.dnd4.yorijori.domain.theme.repository.ThemeRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ThemeService {
	private final ThemeRepository themeRepository;

	public List<ThemeDto> getAllTheme() {
		return themeRepository.findAll().stream().map(ThemeDto::new).collect(Collectors.toList());
	}

}
