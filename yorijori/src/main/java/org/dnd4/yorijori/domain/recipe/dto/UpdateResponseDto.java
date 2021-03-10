package org.dnd4.yorijori.domain.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.dnd4.yorijori.domain.ingredient.entity.Ingredient;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.step.entity.Step;
import org.dnd4.yorijori.domain.theme.entity.Theme;
import org.dnd4.yorijori.domain.user.entity.User;

import java.util.List;

@Data
public class UpdateResponseDto {
    private Long id;
    private String title;
    private String description;
    private String thumbnail;

    private List<Ingredient> mainIngredients;
    private List<Ingredient> subIngredients;
    private List<Theme> themes;
    private List<Step> steps;

    private int time;
    private double starCount;
    private int wishCount;
    private int viewCount;
    private User writer;

    public UpdateResponseDto(Recipe recipe){
        id = recipe.getId();
        title = recipe.getTitle();
        description = recipe.getDescription();
        thumbnail = recipe.getThumbnail();
        mainIngredients = recipe.getMainIngredients();
        subIngredients = recipe.getSubIngredients();
        themes = recipe.getThemes();
        steps = recipe.getSteps();
        time = recipe.getTime();
        starCount= recipe.getAverageStarCount();
        wishCount = recipe.getWishCount();
        viewCount = recipe.getViewCount();
        writer = recipe.getUser();
    }
}
