package org.dnd4.yorijori.domain.user_follow.repository;

import static org.dnd4.yorijori.domain.recipe.entity.QRecipe.recipe;
import static org.dnd4.yorijori.domain.user.entity.QUser.user;
import static org.dnd4.yorijori.domain.user_follow.entity.QUserFollow.userFollow;

import java.util.List;

import org.dnd4.yorijori.domain.common.YesOrNo;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.user.entity.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UserFollowDslRepository extends QuerydslRepositorySupport {

	private final JPAQueryFactory queryFactory;

	public UserFollowDslRepository(JPAQueryFactory queryFactory) {
		super(Recipe.class);
		this.queryFactory = queryFactory;
	}

	public List<Recipe> followingFeed(Long userId, int limit, int offset) {
		List<Recipe> list = queryFactory
				.selectFrom(recipe)
				.where(recipe.user.in(
						JPAExpressions
						.select(userFollow.following)
						.from(userFollow)
						.where(userFollow.follower.id.eq(userId),
								userFollow.followingAlarm.eq(YesOrNo.Y))))
				.orderBy(recipe.createdDate.desc())
				.limit(limit)
				.offset(offset)
				.fetch();
		return list;
	}
	
	public List<Recipe> followerFeed(Long userId, int limit, int offset) {
		List<Recipe> list = queryFactory
				.selectFrom(recipe)
				.where(recipe.user.in(
						JPAExpressions
						.select(userFollow.follower)
						.from(userFollow)
						.where(userFollow.following.id.eq(userId),
								userFollow.followerAlarm.eq(YesOrNo.Y))))
				.orderBy(recipe.createdDate.desc())
				.limit(limit)
				.offset(offset)
				.fetch();
		return list;
	}
	
	public List<User> findFollowingByFollower(User follow, int limit, int offset) {
		List<User> list = queryFactory
				.select(userFollow.following)
				.from(userFollow)
				.where(userFollow.follower.eq(follow),
						userFollow.followerAlarm.eq(YesOrNo.Y))
				.limit(limit)
				.offset(offset)
				.fetch();
		return list;
	}
	
	public List<User> findFollowerByFollowing(User follow, int limit, int offset) {
		List<User> list = queryFactory
				.select(userFollow.follower)
				.from(userFollow)
				.where(userFollow.following.eq(follow),
						userFollow.followingAlarm.eq(YesOrNo.Y))
				.limit(limit)
				.offset(offset)
				.fetch();
		return list;
	}
}
