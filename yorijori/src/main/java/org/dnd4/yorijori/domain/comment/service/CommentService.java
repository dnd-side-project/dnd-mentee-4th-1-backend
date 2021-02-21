package org.dnd4.yorijori.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.domain.comment.dto.RequestCommentDto;
import org.dnd4.yorijori.domain.comment.dto.ResponseCommentDto;
import org.dnd4.yorijori.domain.comment.dto.UpdateCommentDto;
import org.dnd4.yorijori.domain.comment.entity.Comment;
import org.dnd4.yorijori.domain.comment.repository.CommentRepository;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.repository.RecipeRepository;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Transactional
    public List<Comment> findByRecipeId(Long recipeId){

        return commentRepository.findByRecipeId(recipeId);
    }

    @Transactional
    public Long add(RequestCommentDto commentDto){

        Recipe recipe = recipeRepository.findById(commentDto.getRecipeId()).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + commentDto.getRecipeId()));
        User user = userRepository.findById(commentDto.getUserId()).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + commentDto.getUserId()));

        Comment parentComment = null;
        if(commentDto.getPid() != null) parentComment = commentRepository.findById(commentDto.getPid()).orElseThrow(()->new IllegalArgumentException("해당 부모 아이디의 댓글이 없습니다. id : " + commentDto.getPid()));

        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .imageUrl(commentDto.getImageUrl())
                .parent(parentComment)
                .user(user)
                .recipe(recipe)
                .build();

        return commentRepository.save(comment).getId();

    }

    @Transactional
    public Comment get(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 댓글이 없습니다. id : " + id));

        return comment;
    }

    @Transactional
    public Long update(Long id, UpdateCommentDto updateCommentDto){
        Comment comment = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 댓글이 없습니다. id : " + id));
        comment.update(updateCommentDto.getContent(), updateCommentDto.getImageUrl());
        return id;
    }
}
