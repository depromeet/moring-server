package org.depromeet.sambad.moring.meeting.meeting.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingCode;
import org.depromeet.sambad.moring.meeting.meeting.domain.TypesPerMeeting;
import org.depromeet.sambad.moring.meeting.meeting.presentation.exception.MeetingNotFoundException;
import org.depromeet.sambad.moring.meeting.meeting.presentation.request.MeetingPersistRequest;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingResponse;
import org.depromeet.sambad.moring.meeting.meeting.presentation.response.MeetingNameResponse;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberRepository;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
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

		// NOTE: 모임 생성 시점엔 아직 모임장도 모임원 가입이 안된 상태이므로, 오류 발생
		// 해당 로직 추가한 이유가 궁금합니다.
		// meetingQuestionService.createActiveQuestion(meeting, meeting.getOwner(), null);
		return meeting;
	}

	public MeetingResponse getMeetingResponse(Long userId) {
		List<MeetingMember> membersOfUser = meetingMemberRepository.findByUserId(userId);
		List<Meeting> meetings = membersOfUser.stream()
			.map(MeetingMember::getMeeting)
			.toList();

		return MeetingResponse.from(meetings);
	}

	public MeetingNameResponse getMeetingNameByCode(String code) {
		Meeting meeting = meetingRepository.findByCode(MeetingCode.from(code))
			.orElseThrow(MeetingNotFoundException::new);

		return MeetingNameResponse.from(meeting);
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
