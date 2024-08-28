package org.depromeet.sambad.moring.domain.meeting.meeting.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingCode;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.TypesPerMeeting;
import org.depromeet.sambad.moring.domain.meeting.meeting.presentation.exception.MeetingNotFoundException;
import org.depromeet.sambad.moring.domain.meeting.meeting.presentation.exception.NotJoinedAnyMeetingException;
import org.depromeet.sambad.moring.domain.meeting.meeting.presentation.request.MeetingPersistRequest;
import org.depromeet.sambad.moring.domain.meeting.meeting.presentation.response.MeetingNameResponse;
import org.depromeet.sambad.moring.domain.meeting.meeting.presentation.response.MeetingResponse;
import org.depromeet.sambad.moring.domain.meeting.member.application.MeetingMemberRepository;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MeetingService {

	private final MeetingRepository meetingRepository;
	private final MeetingMemberRepository meetingMemberRepository;
	private final MeetingTypeRepository meetingTypeRepository;
	private final MeetingCodeGenerator meetingCodeGenerator;
	private final MeetingMemberValidator meetingMemberValidator;
	private final TypesPerMeetingRepository typesPerMeetingRepository;

	@Transactional
	public Meeting createMeeting(Long userId, MeetingPersistRequest request) {
		meetingMemberValidator.validateMeetingMaxCount(userId);
		Meeting meeting = generateMeeting(request);
		addTypesToMeeting(request, meeting);

		return meeting;
	}

	public MeetingResponse getMeetingResponse(Long userId) {
		List<MeetingMember> membersOfUser = meetingMemberRepository.findByUserId(userId);

		if (membersOfUser.isEmpty()) {
			throw new NotJoinedAnyMeetingException();
		}

		List<Meeting> meetings = membersOfUser.stream()
			.map(MeetingMember::getMeeting)
			.toList();

		return MeetingResponse.of(meetings, MeetingMember.getLastMeetingId(membersOfUser));
	}

	public MeetingNameResponse getMeetingNameByCode(Long userId, String code) {
		Meeting meeting = meetingRepository.findByCode(MeetingCode.from(code))
			.orElseThrow(MeetingNotFoundException::new);

		Optional<MeetingMember> meetingMemberOptional = meetingMemberRepository.findByUserIdAndMeetingId(
			userId, meeting.getId());

		return MeetingNameResponse.of(meeting, meetingMemberOptional.isPresent());
	}

	public Meeting getMeeting(Long meetingId) {
		return meetingRepository.findById(meetingId)
			.orElseThrow(MeetingNotFoundException::new);
	}

	private void addTypesToMeeting(MeetingPersistRequest request, Meeting meeting) {
		List<TypesPerMeeting> types = meetingTypeRepository.findByIdIn(request.meetingTypeIds())
			.stream()
			.map(type -> TypesPerMeeting.of(meeting, type))
			.toList();

		typesPerMeetingRepository.saveAll(types);
	}

	private Meeting generateMeeting(MeetingPersistRequest request) {
		meetingTypeRepository.findByIdIn(request.meetingTypeIds());
		Meeting meeting = Meeting.of(request, meetingCodeGenerator.generate());
		meetingRepository.save(meeting);

		return meeting;
	}
}
