package org.dnd4.yorijori.domain.user_follow.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class FollowKey implements Serializable {
	@Column(name = "following_id")
	Long followingId;

	@Column(name = "follower_id")
	Long followerId;
}
