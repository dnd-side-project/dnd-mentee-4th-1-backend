package org.dnd4.yorijori.domain.user_follow.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.dnd4.yorijori.domain.user.entity.User;

import lombok.Builder;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Embeddable
@NoArgsConstructor
public class FollowKey implements Serializable {
	@Column(name = "following_id")
	Long followingId;

	@Column(name = "follower_id")
	Long followerId;
	
	@Builder
	public FollowKey(User following, User follower) {
		this.followingId=following.getId();
		this.followerId=follower.getId();
	}
}
