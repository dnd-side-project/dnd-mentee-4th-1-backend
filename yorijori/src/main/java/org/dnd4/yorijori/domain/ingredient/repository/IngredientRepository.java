package org.dnd4.yorijori.domain.ingredient.repository;

import org.dnd4.yorijori.domain.ingredient.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    Long deleteByRecipeId(Long recipeId);
}
