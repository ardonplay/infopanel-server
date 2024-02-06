package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.page.PageLocalization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageLocalizationRepository extends JpaRepository<PageLocalization, Integer> {
}
