package org.dnd4.yorijori.domain.comment.controller;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.domain.comment.dto.RequestCommentDto;
import org.dnd4.yorijori.domain.comment.dto.ResponseCommentDto;
import org.dnd4.yorijori.domain.comment.dto.UpdateCommentDto;
import org.dnd4.yorijori.domain.comment.entity.Comment;
import org.dnd4.yorijori.domain.comment.service.CommentService;
import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.common.ResultList;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UpdateRequestDto;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("")
    public ResultList<ResponseCommentDto> getByParentId(
            @RequestParam(required = true) Long pid,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false, defaultValue = "0") int offset){

        List<Comment> comments = commentService.getByParentId(pid,offset, limit);
        return new ResultList<ResponseCommentDto>(comments.stream().map(c->new ResponseCommentDto(c)).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Result<ResponseCommentDto> get(@PathVariable Long id){
        Comment comment = commentService.get(id);
        return new Result<ResponseCommentDto>(new ResponseCommentDto(comment));
    }

    @PutMapping("/{id}")
    public Result<Long> update (@PathVariable Long id, @RequestBody @Validated UpdateCommentDto commentDto){
        Long updateId = commentService.update(id, commentDto);
        return new Result<Long>(updateId);
    }

    @PostMapping("")
    public Result<ResponseCommentDto> add(@RequestBody @Validated RequestCommentDto commentDto){

        Long id = commentService.add(commentDto);
        Comment comment = commentService.get(id);
        return new Result<ResponseCommentDto>(new ResponseCommentDto(comment));

    }
    @DeleteMapping("/{id}")
    public Result<Boolean> delete(@PathVariable Long id){
        commentService.delete(id);
        return new Result<Boolean>(true);
    }


}
