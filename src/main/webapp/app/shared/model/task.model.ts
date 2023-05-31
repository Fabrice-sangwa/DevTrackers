import { IUser } from 'app/shared/model/user.model';
import { IProject } from 'app/shared/model/project.model';
import { Priority } from 'app/shared/model/enumerations/priority.model';
import { State } from 'app/shared/model/enumerations/state.model';

export interface ITask {
  id?: number;
  name?: string;
  description?: string | null;
  priority?: Priority | null;
  state?: State | null;
  assignedUsers?: IUser[] | null;
  users?: IUser[] | null;
  project?: IProject | null;
}

export const defaultValue: Readonly<ITask> = {};
