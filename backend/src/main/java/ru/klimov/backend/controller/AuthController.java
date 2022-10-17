package ru.klimov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klimov.backend.model.dto.UserDto;
import ru.klimov.backend.model.dto.jwt.JwtRequestDto;
import ru.klimov.backend.model.dto.jwt.JwtResponseDto;
import ru.klimov.backend.model.dto.marker.New;
import ru.klimov.backend.model.entity.User;
import ru.klimov.backend.service.AuthService;
import ru.klimov.backend.model.mapper.CoreMapper;

/**
 * Контроллер для авторизации с помощью JWT
 */

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CoreMapper mapper;

    @PostMapping("/login")
    public JwtResponseDto login(@RequestBody @Validated JwtRequestDto authRequest) throws Exception {
        return authService.getToken(authRequest);
    }

    @PostMapping("/registration")
    public UserDto registration(@RequestBody @Validated(New.class) UserDto dto) {
        return mapper.map(authService.registration(mapper.map(dto, User.class)), UserDto.class);
    }
}