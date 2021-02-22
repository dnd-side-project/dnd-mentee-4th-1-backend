package org.dnd4.yorijori.domain.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.domain.common.Result;
import org.dnd4.yorijori.domain.monthly_view.service.MonthlyViewService;
import org.dnd4.yorijori.domain.recipe.dto.RequestDto;
import org.dnd4.yorijori.domain.recipe.dto.ResponseDto;
import org.dnd4.yorijori.domain.recipe.dto.UpdateRequestDto;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.service.RecipeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/recipes")
public class RecipeController {

    private final RecipeService recipeService;
    private final MonthlyViewService monthlyViewService;
	
    @GetMapping("/{id}")
    public Result<ResponseDto> getById (@PathVariable Long id){
        Recipe recipe = recipeService.get(id);
        recipeService.incViewCount(recipe);
        ResponseDto responseDto = new ResponseDto(recipe);
        monthlyViewService.visit(id);
        return new Result<ResponseDto>(responseDto);
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
