package org.dnd4.yorijori.domain.user_follow.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.dnd4.yorijori.domain.common.YesOrNo;
import org.dnd4.yorijori.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
		this.id = FollowKey.builder().follower(follower).following(following).build();
		this.following = following;
		this.follower = follower;
		this.followingAlarm = YesOrNo.Y;
		this.followerAlarm = YesOrNo.Y;
	}
	
	public void followingAlarmOn() {
		this.followingAlarm = YesOrNo.Y;
	}
	public void followingAlarmOff() {
		this.followingAlarm = YesOrNo.N;
	}
	public void followerAlarmOn() {
		this.followerAlarm = YesOrNo.Y;
	}
	public void followerAlarmOff() {
		this.followerAlarm = YesOrNo.N;
	}
}
