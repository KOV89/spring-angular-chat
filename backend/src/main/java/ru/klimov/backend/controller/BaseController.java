package ru.klimov.backend.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.klimov.backend.exception.NotFoundException;
import ru.klimov.backend.model.dto.marker.Exist;
import ru.klimov.backend.model.dto.marker.New;
import ru.klimov.backend.model.entity.marker.IEntity;
import ru.klimov.backend.service.BaseService;
import ru.klimov.backend.model.mapper.CoreMapper;

/**
 * Абстрактный контроллер реализующий базовые операции с объектом (поддерживает маппинг и валидацию через DTO)
 *
 * @param <T>  entity объекта БД
 * @param <D>  dto
 * @param <ID> id entity
 */
@RequiredArgsConstructor
public abstract class BaseController<T extends IEntity<ID>, ID, D> {
    protected final BaseService<T, ID> baseService;
    protected final CoreMapper mapper;
    protected final Class<T> entityClass;
    protected final Class<D> dtoClass;

    @GetMapping
    public Page<D> getAllByPage(
            @RequestParam(value = "page", defaultValue = "0") @NotNull Integer page,
            @RequestParam(value = "size", defaultValue = "50") @NotNull Integer size,
            @RequestParam(value = "field", defaultValue = "id") @NotNull String field,
            @RequestParam(value = "direction", defaultValue = "desc") @NotNull String direction
    ) {
        return baseService.findAllByPage(PageRequest.of(page, size, Sort.Direction.fromString(direction), field))
                .map(entity -> mapper.map(entity, dtoClass));
    }

    @GetMapping("/{id}")
    public D getById(@PathVariable ID id) throws NotFoundException {
        return mapper.map(baseService.findByIdOrThrow(id), dtoClass);
    }

    @PostMapping()
    public D create(@NotNull @Validated(New.class) @RequestBody D dto) {
        return mapper.map(baseService.create(mapper.map(dto, entityClass)), dtoClass);
    }

    @PutMapping()
    public D update(@NotNull @Validated(Exist.class) @RequestBody D dto) throws NotFoundException {
        return mapper.map(baseService.updateOrThrow(mapper.map(dto, entityClass)), dtoClass);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable ID id) throws NotFoundException {
        baseService.deleteByIdOrThrow(id);
    }
}
