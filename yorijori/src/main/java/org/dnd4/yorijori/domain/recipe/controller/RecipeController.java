package org.dnd4.yorijori.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.domain.comment.dto.ResponseCommentDto;
import org.dnd4.yorijori.domain.comment.entity.Comment;
import org.dnd4.yorijori.domain.comment.service.CommentService;
import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.common.ResultList;
import org.dnd4.yorijori.domain.monthly_view.service.MonthlyViewService;
import org.dnd4.yorijori.domain.recipe.dto.RequestDto;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UpdateRequestDto;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.service.RecipeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final MonthlyViewService monthlyViewService;
    private final CommentService commentService;
	
    @GetMapping("/{id}")
    public Result<ResponseDto> getById (@PathVariable Long id){
        Recipe recipe = recipeService.get(id);
        recipeService.incViewCount(recipe);
        ResponseDto responseDto = new ResponseDto(recipe);
        monthlyViewService.visit(id);
        return new Result<ResponseDto>(responseDto);
    }

    @GetMapping("/{id}/comments")
    public ResultList<ResponseCommentDto> getCommentsByRecipeId (
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "0") int offset,
            @RequestParam(required = false, defaultValue = "10") int limit){

        List<Comment> comments = commentService.findByRecipeId(id,offset,limit);

        return new ResultList<ResponseCommentDto>(comments.stream().map(comment -> new ResponseCommentDto(comment)).collect(Collectors.toList()));

    }

    @PutMapping("/{id}")
    public Result<ResponseDto> update (@PathVariable Long id, @RequestBody @Validated UpdateRequestDto reqDto){

        recipeService.update(id, reqDto);
        return new Result<ResponseDto>(new ResponseDto(recipeService.get(id)));
    }

    @PostMapping("")
    public Result<ResponseDto> add (@RequestBody @Validated RequestDto reqDto){
        Long id = recipeService.add(reqDto);
        return new Result<ResponseDto>(new ResponseDto(recipeService.get(id)));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> delete (@PathVariable Long id){
        recipeService.delete(id);
        return new Result<Boolean>(true);
    }
}
