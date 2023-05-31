package com.esisalama.service.mapper;

import com.esisalama.domain.Team;
import com.esisalama.domain.User;
import com.esisalama.service.dto.TeamDTO;
import com.esisalama.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Team} and its DTO {@link TeamDTO}.
 */
@Mapper(componentModel = "spring")
public interface TeamMapper extends EntityMapper<TeamDTO, Team> {
    @Mapping(target = "users", source = "users", qualifiedByName = "userIdSet")
    TeamDTO toDto(Team s);

    @Mapping(target = "removeUsers", ignore = true)
    Team toEntity(TeamDTO teamDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<UserDTO> toDtoUserIdSet(Set<User> user) {
        return user.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }
}
