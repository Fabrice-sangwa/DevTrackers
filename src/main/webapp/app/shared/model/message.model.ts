import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface IMessage {
  id?: number;
  content?: string | null;
  date?: string | null;
  sendees?: IUser[] | null;
}

export const defaultValue: Readonly<IMessage> = {};
