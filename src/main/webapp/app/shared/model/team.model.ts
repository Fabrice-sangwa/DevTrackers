import { IUser } from 'app/shared/model/user.model';

export interface ITeam {
  id?: number;
  name?: string | null;
  users?: IUser[] | null;
}

export const defaultValue: Readonly<ITeam> = {};
