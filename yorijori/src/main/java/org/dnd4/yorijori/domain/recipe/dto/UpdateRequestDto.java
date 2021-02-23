package org.dnd4.yorijori.domain.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UpdateRequestDto {
    private String title;
    private String thumbnail;
    private String description;

    private List<IngredientDto> mainIngredients;
    private List<IngredientDto> subIngredients;
    private List<Long> themeIds;
    private List<StepDto> steps;

    private int time;
    private int viewCount;
    private Long pid;

}


