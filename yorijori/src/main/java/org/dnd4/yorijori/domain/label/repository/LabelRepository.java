package org.dnd4.yorijori.domain.label.repository;

import java.util.List;

import org.dnd4.yorijori.domain.label.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LabelRepository extends JpaRepository<Label, Long> {

	@Query(value = "SELECT recipe_id, COUNT(recipe_id)AS coun FROM label WHERE created_date>= :startDate and created_date<= :endDate GROUP BY recipe_id ORDER BY coun DESC LIMIT :limit", nativeQuery = true)
	List<Long> labelCountDesc(@Param("limit") int limit, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	Label findByUserIdAndRecipeId(Long user_id, Long recipe_id);

}
