package com.esisalama.service.mapper;

import com.esisalama.domain.Project;
import com.esisalama.domain.Task;
import com.esisalama.domain.User;
import com.esisalama.service.dto.ProjectDTO;
import com.esisalama.service.dto.TaskDTO;
import com.esisalama.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring")
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "assignedUsers", source = "assignedUsers", qualifiedByName = "userIdSet")
    @Mapping(target = "users", source = "users", qualifiedByName = "userIdSet")
    @Mapping(target = "project", source = "project", qualifiedByName = "projectId")
    TaskDTO toDto(Task s);

    @Mapping(target = "removeAssignedUsers", ignore = true)
    @Mapping(target = "removeUsers", ignore = true)
    Task toEntity(TaskDTO taskDTO);

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
