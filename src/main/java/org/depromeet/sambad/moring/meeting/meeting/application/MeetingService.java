package org.depromeet.sambad.moring.meeting.meeting.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.meeting.domain.TypesPerMeeting;
import org.depromeet.sambad.moring.meeting.meeting.presentation.request.MeetingPersistRequest;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MeetingService {

	private final MeetingRepository meetingRepository;
	private final MeetingTypeRepository meetingTypeRepository;
	private final MeetingCodeGenerator meetingCodeGenerator;
	private final MeetingMemberValidator meetingMemberValidator;
	private final TypesPerMeetingRepository typesPerMeetingRepository;

	@Transactional
	public Meeting createMeeting(Long userId, MeetingPersistRequest request) {
		meetingMemberValidator.validateMeetingMaxCount(userId);
		Meeting meeting = generateMeeting(request);
		addTypesToMeeting(request, meeting);

		// TODO: MeetingQuestion 모임장 타겟으로 설정 후, MeetingQuestion 추가
		return meeting;
	}

	private void addTypesToMeeting(MeetingPersistRequest request, Meeting meeting) {
		List<TypesPerMeeting> types = meetingTypeRepository.findByIdIn(request.typeIds())
			.stream()
			.map(type -> TypesPerMeeting.of(meeting, type))
			.toList();

		typesPerMeetingRepository.saveAll(types);
	}

	private Meeting generateMeeting(MeetingPersistRequest request) {
		meetingTypeRepository.findByIdIn(request.typeIds());
		Meeting meeting = Meeting.of(request, meetingCodeGenerator.generate());
		meetingRepository.save(meeting);

		return meeting;
	}
}
