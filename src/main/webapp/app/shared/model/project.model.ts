import { ITask } from 'app/shared/model/task.model';
import { IProblem } from 'app/shared/model/problem.model';
import { IDashboard } from 'app/shared/model/dashboard.model';

export interface IProject {
  id?: number;
  name?: string | null;
  objective?: string | null;
  delay?: string | null;
  resources?: string | null;
  version?: string | null;
  tasks?: ITask[] | null;
  problems?: IProblem[] | null;
  dashboard?: IDashboard | null;
}

export const defaultValue: Readonly<IProject> = {};
