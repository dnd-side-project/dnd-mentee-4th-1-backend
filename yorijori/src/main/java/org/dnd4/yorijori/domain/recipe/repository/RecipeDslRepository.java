package org.dnd4.yorijori.domain.recipe.repository;

import static org.dnd4.yorijori.domain.label.entity.QLabel.label;
import static org.dnd4.yorijori.domain.recipe.entity.QRecipe.recipe;
import static org.dnd4.yorijori.domain.ingredient.entity.QIngredient.ingredient;
import static org.dnd4.yorijori.domain.recipe_theme.entity.QRecipeTheme.recipeTheme;
import static org.dnd4.yorijori.domain.theme.entity.QTheme.theme;
import static org.dnd4.yorijori.domain.rating.entity.QRating.rating;

import java.time.LocalDateTime;
import java.util.List;

import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class RecipeDslRepository extends QuerydslRepositorySupport {

	private final JPAQueryFactory queryFactory;

	public RecipeDslRepository(JPAQueryFactory queryFactory) {
		super(Recipe.class);
		this.queryFactory = queryFactory;
	}

	public List<Recipe> findAll(String step, String time, LocalDateTime start, LocalDateTime end,
			String order, String keyword, int limit, int offset) {
		return queryFactory
				.selectFrom(recipe)
				.leftJoin(ingredient).on(recipe.eq(ingredient.recipe))
				.leftJoin(recipeTheme).on(recipe.eq(recipeTheme.recipe))
				.leftJoin(theme).on(theme.eq(recipeTheme.theme))
				.where(
						eqStep(step), 
						eqTime(time), 
						goeStartRecipe(start),
						loeEndRecipe(end), 
						containKeyword(keyword))
				.groupBy(recipe)
				.orderBy(ordered(order)).limit(limit).offset(offset).fetch();
	}

	public List<Double> getAverageStar(Long id){
		return queryFactory
				.select(rating.star.avg().as("starAverage"))
				.from(rating)
				.where(
						eqId(id)
				)
				.groupBy(recipe.id).fetch();
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

	private BooleanExpression eqStep(String step) {
		if (step == null) {
			return null;
		}
		return recipe.step.eq(Integer.parseInt(step));
	}

	private BooleanExpression eqTime(String time) {
		if (time == null) {
			return null;
		}
		return recipe.time.eq(Integer.parseInt(time));
	}

	private BooleanExpression eqId(Long id) {
		if (id == null) {
			return null;
		}
		return recipe.id.eq(id);
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
		return recipe.count().desc();
	}

}
