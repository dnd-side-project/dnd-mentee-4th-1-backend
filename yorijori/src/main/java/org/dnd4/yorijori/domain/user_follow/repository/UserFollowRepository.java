package org.dnd4.yorijori.domain.user_follow.repository;

import java.util.List;
import java.util.Optional;

import org.dnd4.yorijori.domain.user.entity.User;
import org.dnd4.yorijori.domain.user_follow.entity.FollowKey;
import org.dnd4.yorijori.domain.user_follow.entity.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFollowRepository extends JpaRepository<UserFollow, FollowKey> {
	List<User> findFollowingByFollower(User user);
	List<User> findFollewerByFollowing(User user);
	Optional<UserFollow> findByFollowerAndFollowing(User follower, User following);
}
