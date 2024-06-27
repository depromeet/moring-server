package org.depromeet.sambad.moyeo.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long imageFileId;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private LoginProvider loginProvider;
}
