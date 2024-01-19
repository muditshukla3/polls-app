package com.ms.polls.mapper;

import com.ms.polls.dto.UserRecord;
import com.ms.polls.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ModelMapper {

    public UserRecord convertUserEntityToUserRecord(User user){
        return UserRecord.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .name(user.getName())
                .role(user
                        .getRoles()
                        .stream()
                        .map(role -> role.getName().name()).collect(Collectors.toList()))
                .build();
    }
}
