
package io.github.ardonplay.infopanel.server.operations.userOperations.services;

import io.github.ardonplay.infopanel.server.common.entities.User;
import io.github.ardonplay.infopanel.server.operations.userOperations.models.enums.UserRole;


import java.util.Optional;

public interface UserService {


/**
     * Create formalized user in memory. Users must have unique usernames. This method also store
     * user's hashed password with salt.
     *
     * @param username is user's nickname in memory
     * @param password is user's unencrypted password
     * @param userRole is user's privilege
     * @return ScUser which contains unique strUUID in memory, username, password and user's privilege
     */
    Optional<User> createUser(String username, String password, UserRole userRole);

    Optional<String> getUserId(Integer id);


    Optional<User> findByUsername(String username);

}

