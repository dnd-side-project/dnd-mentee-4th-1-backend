package org.dnd4.yorijori.domain.recipe.repository;

import static org.dnd4.yorijori.domain.recipe.entity.QRecipe.recipe;

import java.time.LocalDateTime;
import java.util.List;

import org.dnd4.yorijori.domain.recipe.entity.QRecipe;
import org.dnd4.yorijori.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class RecipeDslRepository extends QuerydslRepositorySupport {

	private final JPAQueryFactory queryFactory;

	public RecipeDslRepository(JPAQueryFactory queryFactory) {
		super(Recipe.class);
		this.queryFactory = queryFactory;
	}
	
	public List<Recipe> findAll(String id, String step, String time, LocalDateTime start, LocalDateTime end) {
		return queryFactory
                .selectFrom(recipe)
                .where(eqId(id),
                        eqStep(step),
                        eqTime(time),
                        goeStart(start),
                        loeEnd(end))
                .fetch();
                      
    }
	private BooleanExpression eqId(String id) {
		if (id == null) {
			return null;
		}
		return recipe.id.eq(Long.parseLong(id));
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
	
	private BooleanExpression goeStart(LocalDateTime start) {
		if (start == null) {
			return null;
		}
		return recipe.createdDate.goe(start);
	}
	
	private BooleanExpression loeEnd(LocalDateTime end) {
		if (end == null) {
			return null;
		}
		return recipe.createdDate.loe(end);
	}
	
}
