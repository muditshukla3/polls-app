package com.ms.polls.dto;

import lombok.Builder;

import java.util.List;

public record UserRecord(String username, String email, String name, List<String> role) {
    @Builder public UserRecord{}
}
