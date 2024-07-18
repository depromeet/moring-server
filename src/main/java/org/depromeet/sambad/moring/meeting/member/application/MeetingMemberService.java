package org.depromeet.sambad.moring.meeting.member.application;

import lombok.RequiredArgsConstructor;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MeetingMemberService {

    private final MeetingMemberRepository meetingMemberRepository;

    public MeetingMember getByUserId(Long userId) {
        return meetingMemberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("MeetingMember not found. userId: " + userId));
    }

    public MeetingMember getById(Long meetingMemberId) {
        return meetingMemberRepository.findById(meetingMemberId)
                .orElseThrow(() -> new IllegalArgumentException("MeetingMember not found. meetingMemberId: " + meetingMemberId));
    }
}
