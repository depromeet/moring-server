package org.depromeet.sambad.moring.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.application.MeetingRepository;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingCode;
import org.depromeet.sambad.moring.meeting.meeting.presentation.exception.MeetingNotFoundException;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberHobby;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.meeting.member.presentation.request.MeetingMemberPersistRequest;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberPersistResponse;
import org.depromeet.sambad.moring.user.domain.User;
import org.depromeet.sambad.moring.user.domain.UserRepository;
import org.depromeet.sambad.moring.user.presentation.exception.NotFoundUserException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MeetingMemberService {

	private final MeetingMemberValidator meetingMemberValidator;
	private final MeetingRepository meetingRepository;
	private final MeetingMemberRepository meetingMemberRepository;
	private final UserRepository userRepository;
	private final HobbyRepository hobbyRepository;
	private final MeetingMemberHobbyRepository meetingMemberHobbyRepository;

	public MeetingMemberListResponse getMeetingMembers(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		return MeetingMemberListResponse.from(meetingMemberRepository.findByMeetingIdOrderByName(meetingId));
	}

	public MeetingMember getByUserId(Long userId) {
		return meetingMemberRepository.findByUserId(userId)
			.orElseThrow(() -> new IllegalArgumentException("MeetingMember not found. userId: " + userId));
	}

	public MeetingMember getById(Long meetingMemberId) {
		return meetingMemberRepository.findById(meetingMemberId)
			.orElseThrow(
				() -> new IllegalArgumentException("MeetingMember not found. meetingMemberId: " + meetingMemberId));
	}

	@Transactional
	public MeetingMemberPersistResponse registerMeetingMember(
		Long userId, String code, MeetingMemberPersistRequest request
	) {
		Meeting meeting = meetingRepository.findByCode(MeetingCode.from(code))
			.orElseThrow(MeetingNotFoundException::new);

		User user = userRepository.findById(userId)
			.orElseThrow(NotFoundUserException::new);

		MeetingMember meetingMember = validateAndCreateMember(userId, request, meeting, user);
		addHobbies(meetingMember, request);

		return MeetingMemberPersistResponse.from(meetingMember);
	}

	private MeetingMember validateAndCreateMember(
		Long userId, MeetingMemberPersistRequest request, Meeting meeting, User user
	) {
		validateMember(userId, request, meeting);
		MeetingMember meetingMember = MeetingMember.createMemberWith(meeting, user, request);
		meetingMemberRepository.save(meetingMember);
		return meetingMember;
	}

	private void validateMember(Long userId, MeetingMemberPersistRequest request, Meeting meeting) {
		if (request.isHost()) {
			meetingMemberValidator.validateHostMaxCount(meeting.getId());
		}

		meetingMemberValidator.validateAlreadyExistMember(userId);
		meetingMemberValidator.validateMeetingMaxCount(userId);
		meetingMemberValidator.validateMeetingMemberMaxCount(meeting.getId());
	}

	private void addHobbies(MeetingMember meetingMember, MeetingMemberPersistRequest request) {
		List<MeetingMemberHobby> hobbies = hobbyRepository.findByIdIn(request.hobbyIds()).stream()
			.map(hobby -> MeetingMemberHobby.of(meetingMember, hobby))
			.toList();

		meetingMemberHobbyRepository.saveAll(hobbies);
	}

	public MeetingMemberListResponse getNextTargets(Long userId) {
		MeetingMember meetingMember = getByUserId(userId);
		Meeting meeting = meetingMember.getMeeting();

		List<MeetingMember> nextTargetMembers = meetingMemberRepository.findNextTargetsByMeeting(meeting.getId(),
			meetingMember.getId());
		return MeetingMemberListResponse.from(nextTargetMembers);
	}
}
