package org.dnd4.yorijori.domain.user_follow.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.dnd4.yorijori.domain.common.YesOrNo;
import org.dnd4.yorijori.domain.ingredient.entity.Ingredient;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.recipe_theme.entity.RecipeTheme;
import org.dnd4.yorijori.domain.step.entity.Step;
import org.dnd4.yorijori.domain.user.entity.User;

@Getter
@NoArgsConstructor
@Entity
public class UserFollow {

	@EmbeddedId
	FollowKey id;

	@ManyToOne
	@MapsId("following_id")
	@JoinColumn(name = "following_id")
	private User following;

	@ManyToOne
	@MapsId("follower_id")
	@JoinColumn(name = "follower_id")
	private User follower;

	private YesOrNo followingAlarm;
	private YesOrNo followerAlarm;

	@Builder
	public UserFollow(User following, User follower) {
		this.following = following;
		this.follower = follower;
		this.followerAlarm = YesOrNo.Y;
		this.followerAlarm = YesOrNo.Y;
	}
}

@SuppressWarnings("serial")
@Embeddable
class FollowKey implements Serializable {
	@Column(name = "following_id")
	Long followingId;

	@Column(name = "follower_id")
	Long followerId;
}