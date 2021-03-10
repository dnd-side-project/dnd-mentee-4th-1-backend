package org.dnd4.yorijori.domain.label.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dnd4.yorijori.domain.common.BaseTimeEntity;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.user.entity.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Label extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false)
    private Recipe recipe;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Label(Recipe recipe, User user){
        this.recipe=recipe;
        this.user=user;
    }
}
