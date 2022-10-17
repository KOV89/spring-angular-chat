package ru.klimov.backend.service;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ru.klimov.backend.exception.JwtAuthenticationException;
import ru.klimov.backend.exception.NotFoundException;
import ru.klimov.backend.model.dto.jwt.JwtRequestDto;
import ru.klimov.backend.model.dto.jwt.JwtResponseDto;
import ru.klimov.backend.model.entity.User;
import ru.klimov.backend.security.jwt.JwtProvider;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    public @NotNull JwtResponseDto getToken(@NotNull JwtRequestDto authRequest) throws Exception {
        final User user = userService.findByUsername(authRequest.getUsername());
        if (Objects.isNull(user)) {
            throw new NotFoundException("Пользователь '" + authRequest.getUsername() + "' не найден");
        }
        if (!user.getPassword().equals(authRequest.getPassword())) {
            throw new JwtAuthenticationException("Пользователь '" + authRequest.getUsername() + "' указал неправильный пароль");
        }
        return new JwtResponseDto(jwtProvider.createToken(user));
    }

    public @NotNull User registration(@NotNull User user) {
        return userService.create(user);
    }
}
