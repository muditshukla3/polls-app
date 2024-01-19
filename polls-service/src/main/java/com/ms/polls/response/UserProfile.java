package com.ms.polls.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class UserProfile {

    private Long id;
    private String username;
    private String name;
    private String email;
    private Instant joinedAt;
    private Long pollCount;
    private Long voteCount;
}
