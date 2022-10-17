export interface Message {
  id: number | undefined;
  text: string;
  user: MessageUser;
  time: Date;
}

export interface MessageUser {
  id: number;
  username?: string;
}
