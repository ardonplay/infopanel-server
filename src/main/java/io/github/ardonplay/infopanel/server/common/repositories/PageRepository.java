package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.PageEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PageRepository extends JpaRepository<PageEntity, Integer> {
}
