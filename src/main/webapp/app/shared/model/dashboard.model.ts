import { IProject } from 'app/shared/model/project.model';

export interface IDashboard {
  id?: number;
  projects?: IProject[] | null;
}

export const defaultValue: Readonly<IDashboard> = {};
