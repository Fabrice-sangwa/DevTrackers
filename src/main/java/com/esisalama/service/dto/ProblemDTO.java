package com.esisalama.service.dto;

import com.esisalama.domain.enumeration.Priority;
import com.esisalama.domain.enumeration.State;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.esisalama.domain.Problem} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProblemDTO implements Serializable {

    private Long id;

    private String description;

    private State state;

    private Priority priority;

    private Set<UserDTO> users = new HashSet<>();

    private ProjectDTO project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Set<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(Set<UserDTO> users) {
        this.users = users;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProblemDTO)) {
            return false;
        }

        ProblemDTO problemDTO = (ProblemDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, problemDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProblemDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", state='" + getState() + "'" +
            ", priority='" + getPriority() + "'" +
            ", users=" + getUsers() +
            ", project=" + getProject() +
            "}";
    }
}
