package com.esisalama.service.mapper;

import com.esisalama.domain.Dashboard;
import com.esisalama.service.dto.DashboardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dashboard} and its DTO {@link DashboardDTO}.
 */
@Mapper(componentModel = "spring")
public interface DashboardMapper extends EntityMapper<DashboardDTO, Dashboard> {}
