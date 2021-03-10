package org.dnd4.yorijori.domain.step.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Step {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "recipe_id", nullable = false)
	private Recipe recipe;

	private String description;
	private String imageUrl;
	private int sequence;

	@Builder
	public Step(String description, String imageUrl, int sequence){
		this.description = description;
		this.imageUrl = imageUrl;
		this.sequence = sequence;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public void update(Recipe recipe, String description, String imageUrl, int sequence ){
		this.recipe =recipe;
		this.description=description;
		this.imageUrl=imageUrl;
		this.sequence = sequence;
	}
}
