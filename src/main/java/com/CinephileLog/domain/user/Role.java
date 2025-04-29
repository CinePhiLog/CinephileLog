package com.CinephileLog.domain.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Role {

    @Id
    private Long roleId;

    private String roleName;
}
