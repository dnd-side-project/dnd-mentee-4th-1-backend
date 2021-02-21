package org.dnd4.yorijori.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class RequestCommentDto {
    private String content;
    private String imageUrl;
    private Long pid;
    private Long recipeId;
    private Long userId;
}
