package org.dnd4.yorijori.domain.recipe.repository;

import static org.dnd4.yorijori.domain.ingredient.entity.QIngredient.ingredient;
import static org.dnd4.yorijori.domain.recipe.entity.QRecipe.recipe;
import static org.dnd4.yorijori.domain.recipe_theme.entity.QRecipeTheme.recipeTheme;
import static org.dnd4.yorijori.domain.theme.entity.QTheme.theme;
import static org.dnd4.yorijori.domain.rating.entity.QRating.rating;

import java.time.LocalDateTime;
import java.util.List;

import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.dnd4.yorijori.domain.user.entity.User;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class RecipeDslRepository extends QuerydslRepositorySupport {

	private final JPAQueryFactory queryFactory;

	public RecipeDslRepository(JPAQueryFactory queryFactory) {
		super(Recipe.class);
		this.queryFactory = queryFactory;
	}

	public List<Recipe> getUserRecipes(User user, int limit, int offset) {
		return queryFactory.selectFrom(recipe).where(recipe.user.eq(user)).orderBy(recipe.createdDate.desc())
				.limit(limit).offset(offset).fetch();
	}

	public List<Recipe> findAll(String stepStart, String stepEnd, String time, LocalDateTime start, LocalDateTime end,
			String order, String keyword, int limit, int offset) {
		return queryFactory.selectFrom(recipe).leftJoin(ingredient).on(recipe.eq(ingredient.recipe))
				.leftJoin(recipeTheme).on(recipe.eq(recipeTheme.recipe)).leftJoin(theme).on(theme.eq(recipeTheme.theme))
				.where(btStep(stepStart, stepEnd), eqTime(time), goeStartRecipe(start), loeEndRecipe(end),
						containKeyword(keyword))
				.groupBy(recipe).orderBy(ordered(order)).limit(limit).offset(offset).fetch();
	}

	public List<Double> getAverageStar(Long id){
		return queryFactory
				.select(rating.star.avg().as("starAverage"))
				.from(rating)
				.where(
						eqId(id)
				)
				.groupBy(rating.recipe.id).fetch();
	}

	private BooleanBuilder containKeyword(String keyword) {
		if (keyword == null) {
			return null;
		}
		BooleanBuilder builder = new BooleanBuilder();
		String[] arStrRegexMultiSpace = keyword.split("\\s+");
		for (String str : arStrRegexMultiSpace) {
			builder.or(recipe.title.contains(str));
			builder.or(ingredient.name.contains(str));
			builder.or(theme.name.contains(str));
		}
		return builder;
	}

	private BooleanExpression btStep(String stepStart, String stepEnd) {
		if (stepStart == null && stepEnd == null) {
			return null;
		}
		if (stepStart == null) {
			return recipe.step.loe(Integer.parseInt(stepEnd));
		}
		if (stepEnd == null) {
			return recipe.step.goe(Integer.parseInt(stepStart));
		}
		return recipe.step.between(Integer.parseInt(stepStart), Integer.parseInt(stepEnd));
	}

	private BooleanExpression eqTime(String time) {
		if (time == null) {
			return null;
		}
		// 60 이상의 수는 예외로 이상 조건 적용, 그 외에는 이하 조건 적용 
		if (Integer.parseInt(time) >= 60)
			return recipe.time.goe(Integer.parseInt(time));
		return recipe.time.loe(Integer.parseInt(time));
	}

	private BooleanExpression eqId(Long id) {
		if (id == null) {
			return null;
		}
		return rating.recipe.id.eq(id);
	}

	private BooleanExpression goeStartRecipe(LocalDateTime start) {
		if (start == null) {
			return null;
		}
		return recipe.createdDate.goe(start);
	}

	private BooleanExpression loeEndRecipe(LocalDateTime end) {
		if (end == null) {
			return null;
		}
		return recipe.createdDate.loe(end);
	}

	private OrderSpecifier<?> ordered(String order) {
		if (order == null) {
			return recipe.count().desc();
		}
		if (order.equals("view")) {
			return recipe.viewCount.desc();
		}
		if (order.equals("latest")) {
			return recipe.createdDate.desc();
		}
		if (order.equals("label")) {
			return recipe.wishCount.desc();
		}
		return recipe.count().desc();
	}

}
