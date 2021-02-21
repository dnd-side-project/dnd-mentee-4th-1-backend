package org.dnd4.yorijori.domain.comment.entity;

import javax.persistence.*;

import lombok.Builder;
import org.dnd4.yorijori.domain.common.BaseTimeEntity;
import org.dnd4.yorijori.domain.common.YesOrNo;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Parent;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", nullable = false)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="pid")
	private Comment parent;

	@OneToMany(mappedBy = "parent")
	private List<Comment> child = new ArrayList<>();

	private String content;
	private String imageUrl;

	@Builder
	public Comment(String content, String imageUrl, Comment parent, User user, Recipe recipe){
		this.content = content;
		this.imageUrl = imageUrl;
		this.parent = parent;
		this.user = user;
		this.recipe = recipe;
	}

	public void update(String content, String imageUrl){
		this.content = content;
		this.imageUrl = imageUrl;
	}

}
