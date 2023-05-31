package com.esisalama.service.mapper;

import com.esisalama.domain.Message;
import com.esisalama.domain.User;
import com.esisalama.service.dto.MessageDTO;
import com.esisalama.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Message} and its DTO {@link MessageDTO}.
 */
@Mapper(componentModel = "spring")
public interface MessageMapper extends EntityMapper<MessageDTO, Message> {
    @Mapping(target = "sendees", source = "sendees", qualifiedByName = "userIdSet")
    MessageDTO toDto(Message s);

    @Mapping(target = "removeSendee", ignore = true)
    Message toEntity(MessageDTO messageDTO);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("userIdSet")
    default Set<UserDTO> toDtoUserIdSet(Set<User> user) {
        return user.stream().map(this::toDtoUserId).collect(Collectors.toSet());
    }
}
