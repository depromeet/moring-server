package org.depromeet.sambad.moring.meeting.member.application;

import static org.depromeet.sambad.moring.event.domain.EventType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.handwaving.application.HandWavingRepository;
import org.depromeet.sambad.moring.meeting.meeting.application.MeetingRepository;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingCode;
import org.depromeet.sambad.moring.meeting.meeting.presentation.exception.MeetingNotFoundException;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberHobby;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.meeting.member.presentation.exception.MeetingMemberNotFoundException;
import org.depromeet.sambad.moring.meeting.member.presentation.exception.NoMeetingMemberInConditionException;
import org.depromeet.sambad.moring.meeting.member.presentation.request.MeetingMemberPersistRequest;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberListResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberPersistResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberResponse;
import org.depromeet.sambad.moring.meeting.member.presentation.response.MeetingMemberSummaryResponse;
import org.depromeet.sambad.moring.meeting.question.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
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
	private final MeetingMemberRandomGenerator meetingMemberRandomGenerator;
	private final MeetingRepository meetingRepository;
	private final MeetingMemberRepository meetingMemberRepository;
	private final UserRepository userRepository;
	private final HobbyRepository hobbyRepository;
	private final MeetingQuestionRepository meetingQuestionRepository;
	private final MeetingMemberHobbyRepository meetingMemberHobbyRepository;
	private final EventService eventService;
	private final HandWavingRepository handWavingRepository;

	@Transactional
	public MeetingMemberPersistResponse registerMeetingMember(
		Long userId, String code, MeetingMemberPersistRequest request
	) {
		Meeting meeting = meetingRepository.findByCode(MeetingCode.from(code))
			.orElseThrow(MeetingNotFoundException::new);

		User user = userRepository.findById(userId)
			.orElseThrow(NotFoundUserException::new);

		MeetingMember meetingMember = validateAndCreateMember(userId, request, meeting, user);
		updateHobbies(meetingMember, request);

		createMeetingQuestionIfFirstMeetingMember(meeting, meetingMember);

		return MeetingMemberPersistResponse.from(meetingMember);
	}

	@Transactional
	public MeetingMemberPersistResponse updateMeetingMember(
		Long userId, Long meetingId, MeetingMemberPersistRequest request
	) {
		Meeting meeting = meetingRepository.findById(meetingId)
			.orElseThrow(MeetingNotFoundException::new);

		User user = userRepository.findById(userId)
			.orElseThrow(NotFoundUserException::new);

		MeetingMember meetingMember = meetingMemberRepository.findByUserIdAndMeetingId(user.getId(), meeting.getId())
			.orElseThrow(MeetingMemberNotFoundException::new);

		meetingMember.update(request);
		updateHobbies(meetingMember, request);

		return MeetingMemberPersistResponse.from(meetingMember);
	}

	public MeetingMemberListResponse getMeetingMembers(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		MeetingMember me = getByUserIdAndMeetingId(userId, meetingId);
		List<MeetingMember> members = meetingMemberRepository.findByMeetingIdOrderByName(meetingId);

		List<MeetingMember> handWavedMembers = handWavingRepository.findHandWavedMembersByMeetingMemberId(me.getId());

		return MeetingMemberListResponse.from(members, handWavedMembers);
	}

	public MeetingMember getByUserIdAndMeetingId(Long userId, Long meetingId) {
		return meetingMemberRepository.findByUserIdAndMeetingId(userId, meetingId)
			.orElseThrow(MeetingMemberNotFoundException::new);
	}

	public MeetingMember getById(Long meetingMemberId) {
		return meetingMemberRepository.findById(meetingMemberId)
			.orElseThrow(MeetingMemberNotFoundException::new);
	}

	public MeetingMemberResponse getMeetingMember(Long userId, Long meetingId, Long memberId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		return MeetingMemberResponse.from(getById(memberId));
	}

	public MeetingMemberResponse getMyMeetingMember(Long userId, Long meetingId) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		return MeetingMemberResponse.from(getByUserIdAndMeetingId(userId, meetingId));
	}

	public MeetingMemberSummaryResponse getRandomMeetingMember(Long userId, Long meetingId,
		List<Long> excludeMemberIds) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);

		List<MeetingMember> nextTargetMembers = meetingMemberRepository.findNextTargetsByMeeting(meetingId, userId,
			excludeMemberIds);

		if (nextTargetMembers.isEmpty()) {
			throw new NoMeetingMemberInConditionException();
		}

		MeetingMember randomMember = meetingMemberRandomGenerator.generate(nextTargetMembers);
		return MeetingMemberSummaryResponse.from(randomMember);
	}

	public boolean isNotEnterAnyMeeting(Long userId) {
		return meetingMemberRepository.findByUserId(userId).isEmpty();
	}

	private void createMeetingQuestionIfFirstMeetingMember(Meeting meeting, MeetingMember meetingMember) {
		Long meetingId = meeting.getId();

		if (meetingMemberRepository.isCountOfMembersIsOne(meetingId)) {
			MeetingQuestion activeMeetingQuestion = MeetingQuestion.createActiveMeetingQuestion(
				meeting, meetingMember, null, LocalDateTime.now(), meeting.getTotalMemberCount());

			meetingQuestionRepository.save(activeMeetingQuestion);
			eventService.publish(meetingMember.getUser().getId(), meetingId, TARGET_MEMBER);
		}
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

		meetingMemberValidator.validateAlreadyExistMember(userId, meeting.getId());
		meetingMemberValidator.validateMeetingMaxCount(userId);
		meetingMemberValidator.validateMeetingMemberMaxCount(meeting.getId());
	}

	private void updateHobbies(MeetingMember meetingMember, MeetingMemberPersistRequest request) {
		List<MeetingMemberHobby> oldHobbies = meetingMember.getMeetingMemberHobbies();
		if (!oldHobbies.isEmpty()) {
			meetingMemberHobbyRepository.deleteAllInBatch(oldHobbies);
		}

		List<MeetingMemberHobby> hobbies = hobbyRepository.findByIdIn(request.hobbyIds()).stream()
			.map(hobby -> MeetingMemberHobby.of(meetingMember, hobby))
			.toList();
		meetingMemberHobbyRepository.saveAll(hobbies);
	}
}
