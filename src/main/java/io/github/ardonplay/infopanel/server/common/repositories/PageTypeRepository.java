package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.PageTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageTypeRepository extends JpaRepository<PageTypeEntity, Integer> {
    PageTypeEntity findFirstByName(String name);
}
