package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.PageElementTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageElementTypeRepository extends JpaRepository<PageElementTypeEntity, Integer> {

    PageElementTypeEntity findFirstByName(String name);
}
