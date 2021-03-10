package org.dnd4.yorijori.domain.theme.repository;

import org.dnd4.yorijori.domain.theme.entity.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {
}
