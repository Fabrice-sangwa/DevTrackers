package com.esisalama.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.esisalama.domain.Dashboard} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DashboardDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DashboardDTO)) {
            return false;
        }

        DashboardDTO dashboardDTO = (DashboardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dashboardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DashboardDTO{" +
            "id=" + getId() +
            "}";
    }
}
