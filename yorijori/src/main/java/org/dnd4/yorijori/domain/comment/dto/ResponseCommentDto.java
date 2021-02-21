package org.dnd4.yorijori.domain.comment.dto;

import lombok.Data;
import org.dnd4.yorijori.domain.comment.entity.Comment;
import org.dnd4.yorijori.domain.common.BaseTimeEntity;
import org.dnd4.yorijori.domain.recipe.dto.UserDto;
import org.dnd4.yorijori.domain.user.entity.User;

@Data
public class ResponseCommentDto extends BaseTimeEntity {
    private Long id;
    private String content;
    private String imageUrl;
    private UserDto writer;
    private Long pid;

    public ResponseCommentDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.imageUrl = comment.getImageUrl();

        User user = comment.getUser();
        this.writer = new UserDto(user.getId(), user.getName(), user.getEmail());
        if(comment.getParent() != null) this.pid = comment.getParent().getId();
    }
}
