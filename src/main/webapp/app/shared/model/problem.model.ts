import { IUser } from 'app/shared/model/user.model';
import { IProject } from 'app/shared/model/project.model';
import { State } from 'app/shared/model/enumerations/state.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';

export interface IProblem {
  id?: number;
  description?: string | null;
  state?: State | null;
  priority?: Priority | null;
  users?: IUser[] | null;
  project?: IProject | null;
}

export const defaultValue: Readonly<IProblem> = {};
