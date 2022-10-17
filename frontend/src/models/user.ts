export interface User {
  id: number | undefined;
  username: string;
  password: string;
  roles: Role[];
}

export interface UserAuth {
  token: string;
}

export type LoginUser = Pick<User, 'username' | 'password'>;
export type RegisterUser = Pick<User, 'username' | 'password'>;

export enum Role {
  ROLE_USER = 'ROLE_USER',
  ROLE_ADMIN = 'ROLE_ADMIN',
}
