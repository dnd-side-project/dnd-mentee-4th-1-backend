package org.dnd4.yorijori.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateCommentDto {
    private String content;
    private String imageUrl;
}
