package ru.klimov.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klimov.backend.model.dto.UserDto;
import ru.klimov.backend.model.entity.User;
import ru.klimov.backend.service.UserService;
import ru.klimov.backend.model.mapper.CoreMapper;
/**
 * Контроллер для работы с пользователями
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController<User, Long, UserDto> {
    public UserController(UserService userService, CoreMapper mapper) {
        super(userService, mapper, User.class, UserDto.class);
    }
}
