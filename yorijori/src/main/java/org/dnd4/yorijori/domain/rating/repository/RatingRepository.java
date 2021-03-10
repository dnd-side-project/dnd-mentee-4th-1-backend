package org.dnd4.yorijori.domain.rating.repository;

import org.dnd4.yorijori.domain.rating.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
