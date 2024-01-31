package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.types.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Integer> {
}
