package org.depromeet.sambad.moring.meeting.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meeting_id")
    private Meeting meeting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_image_id")
    private FileEntity profileImage;

    @OneToMany(mappedBy = "targetMember", fetch = FetchType.LAZY)
    private List<MeetingQuestion> meetingQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "meetingMember", fetch = FetchType.LAZY)
    private List<MeetingMemberHobby> meetingMemberHobbies = new ArrayList<>();

    public boolean isOtherMeeting(MeetingMember targetMember) {
        return !Objects.equals(this.meeting, targetMember.getMeeting());
    }
}
