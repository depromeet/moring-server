package org.depromeet.sambad.moring.meeting.member.domain;

import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingMemberHobby extends BaseTimeEntity {

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

    private MeetingMemberHobby(MeetingMember meetingMember, Hobby hobby) {
        this.meetingMember = meetingMember;
        this.hobby = hobby;
    }

    public static MeetingMemberHobby of(MeetingMember meetingMember, Hobby hobby) {
        return new MeetingMemberHobby(meetingMember, hobby);
    }

    public String getHobbyContent() {
        return hobby.getContent();
    }
}
