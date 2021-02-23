package org.dnd4.yorijori.domain.recipe.dto;

import org.dnd4.yorijori.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
	private Long id;
	private String name;
	private String email;
	private String imageUrl;

	public UserDto(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.email = user.getEmail();
		this.imageUrl = user.getImageUrl();
	}
}
