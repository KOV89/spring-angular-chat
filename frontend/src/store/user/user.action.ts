import { createAction, props } from '@ngrx/store';
import { User } from '../../models/user';
import { UserState } from './user.reducer';

export const setUser = createAction(`[UserState] set user`, props<User>());

export const setToken = createAction(`[UserState] set token`, props<{ token: string }>());

export const setUserState = createAction(`[UserState] set userState`, props<UserState>());

export const clearUserState = createAction(`[UserState] clear user`);
