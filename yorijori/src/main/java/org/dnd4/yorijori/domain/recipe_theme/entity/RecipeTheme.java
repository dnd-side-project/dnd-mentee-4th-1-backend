package org.dnd4.yorijori.domain.recipe_theme.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.theme.entity.Theme;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class RecipeTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme;

    @Builder
    public RecipeTheme(Recipe recipe, Theme theme){
        this.theme = theme;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }
}
