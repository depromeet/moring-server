package org.depromeet.sambad.moring.domain.meeting.member.domain;

import java.util.Objects;

import org.depromeet.sambad.moring.domain.meeting.meeting.presentation.exception.ExceedMaxMeetingCountException;
import org.depromeet.sambad.moring.domain.meeting.member.application.MeetingMemberRepository;
import org.depromeet.sambad.moring.domain.meeting.member.infrastructure.MeetingMemberProperties;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.exception.ExceedMaxMemberCountException;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.exception.ExceedMaxOwnerCountException;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.exception.MeetingMemberAlreadyExistsException;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.exception.MeetingMemberNotFoundException;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.exception.UserNotMemberOfMeetingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MeetingMemberValidator {

	private final MeetingMemberRepository meetingMemberRepository;
	private final MeetingMemberProperties meetingMemberProperties;

	public void validateMeetingMaxCount(Long userId) {
		int maxMeetings = meetingMemberProperties.meetingMaxCount();

		if (meetingMemberRepository.isUserExceedingMaxMeetings(userId, maxMeetings)) {
			throw new ExceedMaxMeetingCountException();
		}
	}

	public void validateAlreadyExistMember(Long userId, Long meetingId) {
		meetingMemberRepository.findByUserIdAndMeetingId(userId, meetingId)
			.ifPresent(meetingMember -> {
				throw new MeetingMemberAlreadyExistsException();
			});
	}

	public void validateMeetingMemberMaxCount(Long meetingId) {
		int maxMeetingMembers = meetingMemberProperties.meetingMemberMaxCount();

		if (meetingMemberRepository.isMeetingExceedingMaxMembers(meetingId, maxMeetingMembers)) {
			throw new ExceedMaxMemberCountException();
		}
	}

	public void validateHostMaxCount(Long meetingId) {
		int maxHostMeetings = meetingMemberProperties.hostMaxCount();

		if (meetingMemberRepository.isOwnerExceedingMaxMeetings(meetingId, maxHostMeetings)) {
			throw new ExceedMaxOwnerCountException();
		}
	}

	public void validateUserIsMemberOfMeeting(Long userId, Long meetingId) {
		if (isNotUserOfMeeting(userId, meetingId)) {
			throw new UserNotMemberOfMeetingException();
		}
	}

	public void validateMemberIsMemberOfMeeting(Long memberId, Long meetingId) {
		if (isNotMemberOfMeeting(memberId, meetingId)) {
			throw new MeetingMemberNotFoundException();
		}
	}

	public boolean isNotUserOfMeeting(Long userId, Long meetingId) {
		return !meetingMemberRepository.isUserMemberOfMeeting(userId, meetingId);
	}

	public boolean isNotMemberOfMeeting(Long memberId, Long meetingId) {
		return meetingMemberRepository.findById(memberId)
			.map(MeetingMember::getMeeting)
			.map(meeting -> !Objects.equals(meeting.getId(), meetingId))
			.orElse(true);
	}
}
