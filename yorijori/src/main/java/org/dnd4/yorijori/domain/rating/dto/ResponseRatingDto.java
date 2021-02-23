package org.dnd4.yorijori.domain.rating.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dnd4.yorijori.domain.rating.entity.Rating;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.user.entity.User;

@Data

public class ResponseRatingDto {
    private Long id;
    private UserDto writer;
    private double star;

    public ResponseRatingDto(Rating rating){
        this.id = rating.getId();

        User user = rating.getUser();
        this.writer = new UserDto(user.getId(), user.getName(), user.getEmail(), user.getImageUrl());
        this.star = rating.getStar();
    }
}
