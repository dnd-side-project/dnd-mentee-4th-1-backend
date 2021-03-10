package org.dnd4.yorijori.domain.recipe.dto;

import java.util.List;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RequestDto {

    private String title;
    private String thumbnail;
    private String description;

    private List<IngredientDto> mainIngredients;
    private List<IngredientDto> subIngredients;
    private List<Long> themeIds;
    private List<Step> steps;

    private int time;
    private Long writerId;
    private Long pid;

    @Embeddable
    @Data
    static public class Step {
        private String imageUrl;
        private String description;
    }


}

