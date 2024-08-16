package org.depromeet.sambad.moring.meeting.question.application;

import static org.depromeet.sambad.moring.event.domain.EventType.*;

import java.util.List;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberRandomGenerator;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberRepository;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class MeetingQuestionTargetReplaceScheduler {

	private final MeetingQuestionRepository meetingQuestionRepository;
	private final MeetingMemberRepository meetingMemberRepository;
	private final MeetingMemberRandomGenerator meetingMemberRandomGenerator;
	private final EventService eventService;

	@Transactional
	@SchedulerLock(name = "meetingQuestionTargetReplace", lockAtLeastFor = "PT5M", lockAtMostFor = "PT10M")
	@Scheduled(fixedDelay = 5 * 60 * 1000)
	public void replace() {
		// 각 모임 별로, 가장 최근 만료된 질문이 비활성화 상태인 경우만 조회
		List<MeetingQuestion> unregisteredQuestions = meetingQuestionRepository.findAllInactiveAndQuestionNotRegistered();

		unregisteredQuestions.forEach(this::replaceEach);
	}

	private void replaceEach(MeetingQuestion meetingQuestion) {
		// 대상자 중 랜덤 선택 후, 배정
		MeetingMember nextTarget = choiceNextTargetMember(meetingQuestion);
		meetingQuestion.replaceTargetMember(nextTarget);

		// 대상자 변경 이벤트 발행
		publishTargetMemberEvent(nextTarget);

		log.info("Replaced target member of meeting question. meetingQuestionId: {}, targetMemberId: {}",
			meetingQuestion.getId(), nextTarget.getId());
	}

	private MeetingMember choiceNextTargetMember(MeetingQuestion meetingQuestion) {
		// 재배치할 질문 대상자들을 조회. 현재 타겟은 대상에서 제외.
		List<MeetingMember> nextTargets = meetingMemberRepository.findNextTargetsByMeeting(
			meetingQuestion.getMeeting().getId(), meetingQuestion.getTargetMember().getId(), List.of());

		// 대상자가 없는 경우, 기존 대상자를 유지
		if (nextTargets.isEmpty()) {
			return meetingQuestion.getTargetMember();
		}

		// 대상자 중 랜덤 선택
		return meetingMemberRandomGenerator.generate(nextTargets);
	}

	private void publishTargetMemberEvent(MeetingMember nextTarget) {
		Long nextTargetMemberUserId = nextTarget.getUser().getId();
		Long meetingId = nextTarget.getMeeting().getId();

		eventService.inactivateLastEventByType(nextTargetMemberUserId, meetingId, TARGET_MEMBER);
		eventService.publish(nextTargetMemberUserId, meetingId, TARGET_MEMBER);
	}
}
