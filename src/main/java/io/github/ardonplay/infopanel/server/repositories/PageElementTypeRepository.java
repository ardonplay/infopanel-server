package io.github.ardonplay.infopanel.server.repositories;

import io.github.ardonplay.infopanel.server.models.entities.PageElementTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageElementTypeRepository extends JpaRepository<PageElementTypeEntity, Integer> {

    PageElementTypeEntity findFirstByName(String name);
}
