package io.github.ardonplay.infopanel.server.common.repositories;

import io.github.ardonplay.infopanel.server.common.entities.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);
}
