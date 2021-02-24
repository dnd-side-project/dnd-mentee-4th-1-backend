package org.dnd4.yorijori.domain.recipe.service;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.domain.comment.repository.CommentRepository;
import org.dnd4.yorijori.domain.common.YesOrNo;
import org.dnd4.yorijori.domain.ingredient.entity.Ingredient;
import org.dnd4.yorijori.domain.ingredient.repository.IngredientRepository;
import org.dnd4.yorijori.domain.label.repository.LabelRepository;
import org.dnd4.yorijori.domain.rating.repository.RatingRepository;
import org.dnd4.yorijori.domain.recipe.dto.RequestDto;
import org.dnd4.yorijori.domain.recipe.dto.UpdateRequestDto;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.repository.RecipeDslRepository;
import org.dnd4.yorijori.domain.recipe.repository.RecipeRepository;
import org.dnd4.yorijori.domain.recipe_theme.entity.RecipeTheme;
import org.dnd4.yorijori.domain.recipe_theme.repository.RecipeThemeRepository;
import org.dnd4.yorijori.domain.step.entity.Step;
import org.dnd4.yorijori.domain.step.repository.StepRepository;
import org.dnd4.yorijori.domain.theme.entity.Theme;
import org.dnd4.yorijori.domain.theme.repository.ThemeRepository;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeDslRepository recipeDslRepository;

    private final IngredientRepository ingredientRepository;

    private final RecipeThemeRepository recipeThemeRepository;
    private final ThemeRepository themeRepository;

    private final StepRepository stepRepository;
    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final LabelRepository labelRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long add(RequestDto requestDto){

        User user = userRepository.getOne(requestDto.getWriterId());

        List<Theme> themes = themeRepository.findAllById(requestDto.getThemeIds());
        List<RecipeTheme> recipeThemes = themes.stream()
                .map(t-> RecipeTheme.builder()
                        .theme(t)
                        .build())
                .collect(Collectors.toList());

        //recipeThemeRepository.saveAll(recipeThemes);

        List<Ingredient> ingredients= requestDto.getMainIngredients().stream()
                .map((i)->Ingredient.builder()
                        .name(i.getName())
                        .isSub(YesOrNo.N)
                        .build())
                .collect(Collectors.toList());


        if(requestDto.getSubIngredients() != null){
            List<Ingredient> subIngredients = requestDto.getSubIngredients().stream()
                    .map((i)->Ingredient.builder()
                            .name(i.getName())
                            .isSub(YesOrNo.Y)
                            .build())
                    .collect(Collectors.toList());

            ingredients.addAll(subIngredients);
        }


        AtomicInteger sequence = new AtomicInteger();
        List<Step> steps = requestDto.getSteps().stream()
                .map((s)->Step.builder()
                        .description(s.getDescription())
                        .imageUrl(s.getImageUrl())
                        .sequence(sequence.getAndIncrement())
                        .build())
                .collect(Collectors.toList());


        Recipe recipe = Recipe.builder()
                .title(requestDto.getTitle())
                .description(requestDto.getDescription())
                .step(requestDto.getSteps().size())
                .time(requestDto.getTime())
                .thumbnail(requestDto.getThumbnail())
                .user(user)
                .ingredients(ingredients)
                .recipeThemes(recipeThemes)
                .steps(steps)
                .build();

        if(requestDto.getPid() != null){
            Recipe parentRecipe = recipeRepository.findById(requestDto.getPid()).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + requestDto.getPid()));
            recipe.setParent(parentRecipe);
        }
        recipeRepository.save(recipe);
        return recipe.getId();
    }

    @Transactional
    public Long update(Long id, UpdateRequestDto updateRequestDto){

        //recipe 수정
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + id));


        List<Theme> themes = themeRepository.findAllById(updateRequestDto.getThemeIds());
        List<RecipeTheme> recipeThemes = themes.stream()
                .map(t-> RecipeTheme.builder()
                        .theme(t)
                        .build())
                .collect(Collectors.toList());


        //step update
        updateRequestDto.getSteps().forEach(stepDto -> {

            Step step = stepRepository.getOne(stepDto.getId());
            step.update(recipe , stepDto.getDescription(), stepDto.getImageUrl(), stepDto.getSequence());
        });

        //delete ingredients
        ingredientRepository.deleteByRecipeId(id);

        List<Ingredient> ingredients= updateRequestDto.getMainIngredients().stream()
                .map((i)->Ingredient.builder()
                        .name(i.getName())
                        .isSub(YesOrNo.N)
                        .build())
                .collect(Collectors.toList());


        if(updateRequestDto.getSubIngredients() != null){
            List<Ingredient> subIngredients = updateRequestDto.getSubIngredients().stream()
                    .map((i)->Ingredient.builder()
                            .name(i.getName())
                            .isSub(YesOrNo.Y)
                            .build())
                    .collect(Collectors.toList());

            ingredients.addAll(subIngredients);
        }

        if(updateRequestDto.getPid() == null){
            recipe.update(updateRequestDto.getTitle(),
                    updateRequestDto.getDescription(),
                    updateRequestDto.getSteps().size(),
                    updateRequestDto.getTime(),
                    updateRequestDto.getViewCount(),
                    updateRequestDto.getWishCount(),
                    updateRequestDto.getThumbnail(),
                    ingredients,
                    recipeThemes,
                    null
            );
            return id;
        }

        Recipe parentRecipe = recipeRepository.findById(updateRequestDto.getPid()).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + updateRequestDto.getPid()));

        recipe.update(updateRequestDto.getTitle(),
                updateRequestDto.getDescription(),
                updateRequestDto.getSteps().size(),
                updateRequestDto.getTime(),
                updateRequestDto.getViewCount(),
                updateRequestDto.getWishCount(),
                updateRequestDto.getThumbnail(),
                ingredients,
                recipeThemes,
                parentRecipe
        );

        return id;
    }

    @Transactional
    public void delete(Long id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + id));
        recipeRepository.delete(recipe);
    }

    @Transactional
    public Recipe get(Long id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + id));

        return recipe;
    }


    @Transactional
    public void updateStarAverage (Long id){
        Recipe recipe = recipeRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + id));
        Double starAverage = recipeDslRepository.getAverageStar(id).get(0);

        recipe.updateStarCount(starAverage);
    }

    @Transactional
    public void incViewCount(Recipe recipe){
        recipe.incViewCount();
    }
   
    @Transactional
    public void incWishCount(Recipe recipe){
        recipe.incWishCount();
    }
    
    @Transactional
    public void decWishCount(Recipe recipe){
        recipe.decWishCount();

    }
}
