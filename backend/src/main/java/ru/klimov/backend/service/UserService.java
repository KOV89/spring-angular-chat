package ru.klimov.backend.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.klimov.backend.model.entity.User;
import ru.klimov.backend.repository.UserRepository;

@Service
public class UserService extends BaseServiceImpl<User, Long> {

    public UserService(UserRepository userRepository) {
        super(userRepository, User.class);
    }

    public @Nullable User findByUsername(@NotNull String username) {
        return ((UserRepository) jpaRepository).findByUsername(username);
    }
}
