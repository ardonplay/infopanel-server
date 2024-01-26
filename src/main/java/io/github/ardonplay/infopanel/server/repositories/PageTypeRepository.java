package io.github.ardonplay.infopanel.server.repositories;

import io.github.ardonplay.infopanel.server.models.entities.PageTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageTypeRepository extends JpaRepository<PageTypeEntity, Integer> {
    PageTypeEntity findFirstByName(String name);
}
