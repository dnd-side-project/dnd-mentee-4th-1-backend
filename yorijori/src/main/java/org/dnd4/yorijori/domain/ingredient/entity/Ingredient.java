package org.dnd4.yorijori.domain.ingredient.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dnd4.yorijori.domain.common.YesOrNo;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    private String name;

    @Enumerated(EnumType.STRING)
    private YesOrNo isSub;
    @Builder
    public Ingredient(String name,YesOrNo isSub){
        this.name = name;
        this.isSub = isSub;
    }
    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

}
