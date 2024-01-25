package io.github.ardonplay.infopanel.server.repositories;

import io.github.ardonplay.infopanel.server.models.entities.PageElementType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageElementTypeRepository extends JpaRepository<PageElementType, Integer> {

    PageElementType findFirstByName(String name);
}