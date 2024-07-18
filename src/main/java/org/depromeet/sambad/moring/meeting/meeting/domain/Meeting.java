package org.depromeet.sambad.moring.meeting.meeting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meeting extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    private String name;

    private String code;

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<MeetingMember> meetingMembers = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<TypesPerMeeting> typesPerMeetings = new ArrayList<>();

    @OneToMany(mappedBy = "meeting", fetch = FetchType.LAZY)
    private List<MeetingQuestion> meetingQuestions = new ArrayList<>();
}
