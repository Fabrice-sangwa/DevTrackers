package com.esisalama.service.mapper;

import com.esisalama.domain.Dashboard;
import com.esisalama.domain.Project;
import com.esisalama.service.dto.DashboardDTO;
import com.esisalama.service.dto.ProjectDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Project} and its DTO {@link ProjectDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProjectMapper extends EntityMapper<ProjectDTO, Project> {
    @Mapping(target = "dashboard", source = "dashboard", qualifiedByName = "dashboardId")
    ProjectDTO toDto(Project s);

    @Named("dashboardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DashboardDTO toDtoDashboardId(Dashboard dashboard);
}
