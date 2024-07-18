package org.depromeet.sambad.moring.meeting.meeting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingType extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_type_id")
    private Long id;

    private String content;
}
