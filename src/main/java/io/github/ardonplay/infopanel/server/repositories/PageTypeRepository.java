package io.github.ardonplay.infopanel.server.repositories;

import io.github.ardonplay.infopanel.server.models.entities.PageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageTypeRepository extends JpaRepository<PageType, Integer> {
    PageType findFirstByName(String name);
}
