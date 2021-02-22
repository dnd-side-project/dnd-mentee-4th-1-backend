package org.dnd4.yorijori.domain.theme.dto;

import org.dnd4.yorijori.domain.theme.entity.Theme;

import lombok.Getter;

@Getter
public class ThemeDto {
	private Long id;
	private String name;

	public ThemeDto(Theme entity) {
		this.id = entity.getId();
		this.name = entity.getName();
	}
}
