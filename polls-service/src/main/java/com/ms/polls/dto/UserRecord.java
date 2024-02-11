package com.ms.polls.dto;

import com.ms.polls.entity.Role;
import lombok.Builder;

import java.util.List;

public record UserRecord(String username, String email, String name, Role role) {
    @Builder public UserRecord{}
}
