package org.dnd4.yorijori.domain.rating.service;

import lombok.RequiredArgsConstructor;
import org.dnd4.yorijori.domain.rating.dto.RequestRatingDto;
import org.dnd4.yorijori.domain.rating.entity.Rating;
import org.dnd4.yorijori.domain.rating.repository.RatingDslRepository;
import org.dnd4.yorijori.domain.rating.repository.RatingRepository;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe.repository.RecipeRepository;
import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RatingDslRepository ratingDslRepository;

    @Transactional
    public Long add(Long recipeId, Long userId, double star){
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 레시피가 없습니다. id : " + recipeId));
        User user = userRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("해당 아이디의 유저가 없습니다. id : " + userId));
        Rating rating = Rating.builder().user(user).recipe(recipe).star(star).build();



        return ratingRepository.save(rating).getId();
    }

    @Transactional
    public Rating get(Long id){
        Rating rating = ratingRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 아이디의 rating 이없습니다. id : " + id));

        return rating;
    }

    @Transactional
    public List<Rating> findByRecipeId(Long recipeId,int offset,int limit){
        return ratingDslRepository.findByRecipeId(recipeId, offset, limit);
    }
}
