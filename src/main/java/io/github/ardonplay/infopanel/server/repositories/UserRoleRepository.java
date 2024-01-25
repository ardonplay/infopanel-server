package io.github.ardonplay.infopanel.server.repositories;

import io.github.ardonplay.infopanel.server.models.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}
