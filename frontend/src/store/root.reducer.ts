import { ActionReducerMap } from '@ngrx/store';
import { userReducers, UserState } from './user/user.reducer';

export interface State {
  userState: UserState;
}

export const reducers: ActionReducerMap<State> = {
  userState: userReducers,
};
