package ai.cryptocast.core.services;

import ai.cryptocast.core.domain.User;

import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
}
