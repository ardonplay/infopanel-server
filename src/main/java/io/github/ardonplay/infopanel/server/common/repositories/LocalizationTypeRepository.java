package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.types.LocalizationTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalizationTypeRepository extends JpaRepository<LocalizationTypeEntity, Integer> {
}
