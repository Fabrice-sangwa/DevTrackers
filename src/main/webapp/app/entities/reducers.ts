import project from 'app/entities/project/project.reducer';
import task from 'app/entities/task/task.reducer';
import problem from 'app/entities/problem/problem.reducer';
import team from 'app/entities/team/team.reducer';
import message from 'app/entities/message/message.reducer';
import dashboard from 'app/entities/dashboard/dashboard.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  project,
  task,
  problem,
  team,
  message,
  dashboard,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
