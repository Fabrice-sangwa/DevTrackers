package com.esisalama.service.mapper;

import com.esisalama.domain.Problem;
import com.esisalama.domain.Project;
import com.esisalama.domain.User;
import com.esisalama.service.dto.ProblemDTO;
import com.esisalama.service.dto.ProjectDTO;
import com.esisalama.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Problem} and its DTO {@link ProblemDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProblemMapper extends EntityMapper<ProblemDTO, Problem> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userIdSet")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    ProblemDTO toDto(Problem s);

    @Mapping(target = "removeUsers", ignore = true)
    Problem toEntity(ProblemDTO problemDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<UserDTO> toDtoUserIdSet(Set<User> user) {
        return user.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }

    @Named("projectId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ProjectDTO toDtoProjectId(Project project);
}
