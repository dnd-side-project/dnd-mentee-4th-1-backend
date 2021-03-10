package org.dnd4.yorijori.domain.step.repository;

import org.dnd4.yorijori.domain.step.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Step, Long> {
}
