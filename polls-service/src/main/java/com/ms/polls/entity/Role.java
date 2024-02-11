package com.ms.polls.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

public enum Role {
    USER,
    ADMIN
}
