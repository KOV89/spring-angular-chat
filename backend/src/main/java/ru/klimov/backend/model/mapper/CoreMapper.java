package ru.klimov.backend.model.mapper;

import lombok.SneakyThrows;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import org.springframework.stereotype.Component;
import ru.klimov.backend.model.dto.MessageDto;
import ru.klimov.backend.model.dto.MessageUserDto;
import ru.klimov.backend.model.entity.Message;
import ru.klimov.backend.model.entity.User;
import ru.klimov.backend.model.dto.UserDto;
import ru.klimov.backend.service.UserService;

/**
 * Конфигурация адапрета для преобразования Entity в Dto и обратно
 */
@Component
public class CoreMapper extends ConfigurableMapper {
    private final UserService userService;
    public CoreMapper(UserService userService) {
        super(true);
        this.userService = userService;
    }

    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(User.class, UserDto.class)
                .byDefault()
                .register();

        factory.classMap(Message.class, MessageDto.class)
                .byDefault()
                .customize(new CustomMapper<>() {
                    @Override
                    public void mapAtoB(Message message, MessageDto messageDto, MappingContext context) {
                        super.mapAtoB(message, messageDto, context);
                        messageDto.setUser(new MessageUserDto(message.getUser().getId(), message.getUser().getUsername()));
                    }

                    @Override
                    @SneakyThrows
                    public void mapBtoA(MessageDto messageDto, Message message, MappingContext context) {
                        super.mapBtoA(messageDto, message, context);
                        message.setUser(userService.findByIdOrThrow(messageDto.getUser().getId()));
                    }
                })
                .register();
    }
}
