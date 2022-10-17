package ru.klimov.backend.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.klimov.backend.exception.NotFoundException;
import ru.klimov.backend.model.entity.marker.IEntity;
/**
 * Абстрактный класс реализующий базовые операции с объектом
 *
 * @param <T>  entity объекта БД
 * @param <ID> id entity
 */
abstract class BaseServiceImpl<T extends IEntity<ID>, ID> implements BaseService<T, ID> {
    protected final JpaRepository<T, ID> jpaRepository;
    protected final Class<T> entityClass;

    public BaseServiceImpl(@NotNull JpaRepository<T, ID> jpaRepository, @NotNull Class<T> entityClass) {
        this.jpaRepository = jpaRepository;
        this.entityClass = entityClass;
    }

    @Override
    public @Nullable T findById(@NotNull ID id) {
        return jpaRepository.findById(id).orElse(null);
    }

    @Override
    public @NotNull T findByIdOrThrow(@NotNull ID id) throws NotFoundException {
        return findOrThrow(id);
    }

    @Override
    public @NotNull Page<T> findAllByPage(@NotNull Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public @NotNull T create(@NotNull T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public @NotNull T update(@NotNull T entity) {
        return jpaRepository.save(entity);
    }

    @Override
    public @NotNull T updateOrThrow(@NotNull T entity) throws NotFoundException {
        findOrThrow(entity.getId());
        return jpaRepository.save(entity);
    }

    @Override
    public void deleteById(@NotNull ID id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void deleteByIdOrThrow(@NotNull ID id) throws NotFoundException {
        findOrThrow(id);
        jpaRepository.deleteById(id);
    }

    private @NotNull T findOrThrow(@NotNull ID id) throws NotFoundException {
        return jpaRepository.findById(id).orElseThrow(() -> new NotFoundException("Объект '" + entityClass.getSimpleName() + "' (ID = " + id + ") не найден"));
    }
}
