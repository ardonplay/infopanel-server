package io.github.ardonplay.infopanel.server.repositories;

import io.github.ardonplay.infopanel.server.models.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PageRepository extends JpaRepository<PageEntity, Integer> {
}
