package com.esisalama.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.esisalama.domain.Project} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProjectDTO implements Serializable {

    private Long id;

    private String name;

    private String objective;

    private String delay;

    private String resources;

    private String version;

    private DashboardDTO dashboard;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public DashboardDTO getDashboard() {
        return dashboard;
    }

    public void setDashboard(DashboardDTO dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectDTO)) {
            return false;
        }

        ProjectDTO projectDTO = (ProjectDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", objective='" + getObjective() + "'" +
            ", delay='" + getDelay() + "'" +
            ", resources='" + getResources() + "'" +
            ", version='" + getVersion() + "'" +
            ", dashboard=" + getDashboard() +
            "}";
    }
}
