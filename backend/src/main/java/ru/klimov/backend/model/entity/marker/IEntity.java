package ru.klimov.backend.model.entity.marker;

import org.jetbrains.annotations.Nullable;
/**
 * Интерфейс для работы с Id, используется в абстракции
 * @param <ID> тип Id
 */
public interface IEntity<ID> {
    @Nullable ID getId();
}
