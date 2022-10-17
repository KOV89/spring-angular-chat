package ru.klimov.backend.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.klimov.backend.exception.NotFoundException;
import ru.klimov.backend.model.entity.marker.IEntity;
/**
 * Интерфейс содержащий базовые операции с объектом
 *
 * @param <T>  entity объекта БД
 * @param <ID> id entity
 */
public interface BaseService<T extends IEntity<ID>, ID> {
    @Nullable T findById(@NotNull ID id);
    @NotNull T findByIdOrThrow(@NotNull ID id) throws NotFoundException;
    @NotNull Page<T> findAllByPage(@NotNull Pageable pageable);
    @NotNull T create(@NotNull T entity);
    @NotNull T update(@NotNull T entity);
    @NotNull T updateOrThrow(@NotNull T entity) throws NotFoundException;
    void deleteById(@NotNull ID id);
    void deleteByIdOrThrow(@NotNull ID id) throws NotFoundException;
}
