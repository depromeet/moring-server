package org.depromeet.sambad.moring.meeting.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hobby_id")
    private Long id;

    private String content;
}
