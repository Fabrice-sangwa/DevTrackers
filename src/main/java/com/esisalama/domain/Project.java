package com.esisalama.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "objective")
    private String objective;

    @Column(name = "delay")
    private String delay;

    @Column(name = "resources")
    private String resources;

    @Column(name = "version")
    private String version;

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "assignedUsers", "users", "project" }, allowSetters = true)
    private Set<Task> tasks = new HashSet<>();

    @OneToMany(mappedBy = "project")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "users", "project" }, allowSetters = true)
    private Set<Problem> problems = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "projects" }, allowSetters = true)
    private Dashboard dashboard;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Project id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Project name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return this.objective;
    }

    public Project objective(String objective) {
        this.setObjective(objective);
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getDelay() {
        return this.delay;
    }

    public Project delay(String delay) {
        this.setDelay(delay);
        return this;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getResources() {
        return this.resources;
    }

    public Project resources(String resources) {
        this.setResources(resources);
        return this;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getVersion() {
        return this.version;
    }

    public Project version(String version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Set<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<Task> tasks) {
        if (this.tasks != null) {
            this.tasks.forEach(i -> i.setProject(null));
        }
        if (tasks != null) {
            tasks.forEach(i -> i.setProject(this));
        }
        this.tasks = tasks;
    }

    public Project tasks(Set<Task> tasks) {
        this.setTasks(tasks);
        return this;
    }

    public Project addTasks(Task task) {
        this.tasks.add(task);
        task.setProject(this);
        return this;
    }

    public Project removeTasks(Task task) {
        this.tasks.remove(task);
        task.setProject(null);
        return this;
    }

    public Set<Problem> getProblems() {
        return this.problems;
    }

    public void setProblems(Set<Problem> problems) {
        if (this.problems != null) {
            this.problems.forEach(i -> i.setProject(null));
        }
        if (problems != null) {
            problems.forEach(i -> i.setProject(this));
        }
        this.problems = problems;
    }

    public Project problems(Set<Problem> problems) {
        this.setProblems(problems);
        return this;
    }

    public Project addProblems(Problem problem) {
        this.problems.add(problem);
        problem.setProject(this);
        return this;
    }

    public Project removeProblems(Problem problem) {
        this.problems.remove(problem);
        problem.setProject(null);
        return this;
    }

    public Dashboard getDashboard() {
        return this.dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Project dashboard(Dashboard dashboard) {
        this.setDashboard(dashboard);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && id.equals(((Project) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Project{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", objective='" + getObjective() + "'" +
            ", delay='" + getDelay() + "'" +
            ", resources='" + getResources() + "'" +
            ", version='" + getVersion() + "'" +
            "}";
    }
}
