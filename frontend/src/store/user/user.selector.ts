import { createFeatureSelector, createSelector } from '@ngrx/store';
import { UserState } from './user.reducer';

export const getUserState = createFeatureSelector<UserState>('userState');
export const getUser = createSelector(getUserState, (state: UserState) => state.user);
export const getToken = createSelector(getUserState, (state: UserState) => state.token);
