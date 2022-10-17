import { Action, ActionReducer } from '@ngrx/store';

/**
 * Синхронизирует содержимое стейта и локального хранилища
 * <T> стейт
 * @param initialState начальное состояние стейта
 * @param _reducer редюсеры стейта
 * @param localStorageKey ключ для локального хранилища
 */
export function persistStateReducer<T>(initialState: T, _reducer: ActionReducer<T>, localStorageKey: string): ActionReducer<T> {
  return (state: T | undefined, action: Action) => {
    if (state === undefined) {
      const persisted = localStorage.getItem(localStorageKey);
      return persisted ? JSON.parse(persisted) : _reducer(state, action);
    }
    const nextState = _reducer(state, action);
    localStorage.setItem(localStorageKey, JSON.stringify(nextState));
    return nextState;
  };
}
