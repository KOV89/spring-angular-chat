import { User } from '../../models/user';
import { Action, createReducer, on } from '@ngrx/store';
import { persistStateReducer } from '../util';
import { clearUserState, setToken, setUser, setUserState } from './user.action';

export interface UserState {
  user: User | undefined;
  token: string | undefined;
}

const initialState: UserState = {
  user: undefined,
  token: undefined,
};

const reducers = createReducer(
  initialState,
  on(setUser, (state, user) => ({
    ...state,
    user,
  })),
  on(setToken, (state, { token }) => ({
    ...state,
    token,
  })),
  on(setUserState, (state, userState) => ({
    ...state,
    ...userState,
  })),
  on(clearUserState, () => ({
    ...initialState,
  })),
);

export const userReducers = (state: UserState | undefined, action: Action) =>
  persistStateReducer<UserState>(initialState, reducers, '__user')(state, action);
