package org.depromeet.sambad.moring.meeting.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMemberHobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_member_hobby_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_member_id")
    private MeetingMember meetingMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hobby_id")
    private Hobby hobby;
}
