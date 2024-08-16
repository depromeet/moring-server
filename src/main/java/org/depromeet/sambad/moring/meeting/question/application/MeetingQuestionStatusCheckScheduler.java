package org.depromeet.sambad.moring.meeting.question.application;

import static java.time.LocalDateTime.*;
import static org.depromeet.sambad.moring.event.domain.EventType.*;
import static org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestionStatus.*;

import java.util.List;

import org.depromeet.sambad.moring.event.application.EventService;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
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
public class MeetingQuestionStatusCheckScheduler {

	private final MeetingQuestionRepository meetingQuestionRepository;
	private final EventService eventService;

	@Scheduled(fixedDelay = 10 * 1000)
	@SchedulerLock(name = "meetingQuestionStatus", lockAtLeastFor = "PT10S", lockAtMostFor = "PT30S")
	@Transactional
	public void inactivate() {
		// 활성 상태이지만 만료 시간이 지나 INACTIVE 상태가 되어야 하는 질문 목록 조회
		List<MeetingQuestion> targets = meetingQuestionRepository.findAllByStatusAndExpiredAtBefore(ACTIVE, now());

		// 질문 비활성화 처리
		targets.forEach(MeetingQuestion::updateStatusToInactive);

		// 해당 모임의 다음 질문 활성화 처리 및 이벤트 발행
		targets.stream()
			.map(MeetingQuestion::getMeeting)
			.forEach(this::activate);

		// 결과 로깅
		logResults(targets);
	}

	private void activate(Meeting meeting) {
		Long meetingId = meeting.getId();

		meetingQuestionRepository.findFirstByMeetingIdAndStatus(meetingId, NOT_STARTED)
			.ifPresent(targetMeetingQuestion -> {
				targetMeetingQuestion.updateStatusToActive(now());
				reissueTargetMemberEvent(targetMeetingQuestion);
				log.info("Activated not started meeting question. {}", targetMeetingQuestion.getId());
			});
	}

	private void reissueTargetMemberEvent(MeetingQuestion target) {
		Long meetingId = target.getMeeting().getId();
		Long targetMemberUserId = target.getTargetMember().getUser().getId();

		eventService.inactivateLastEventsOfAllMemberByType(meetingId, QUESTION_REGISTERED);
		eventService.inactivateLastEventByType(targetMemberUserId, meetingId, TARGET_MEMBER);
		eventService.publish(targetMemberUserId, meetingId, TARGET_MEMBER);
	}

	private void logResults(List<MeetingQuestion> targets) {
		if (!targets.isEmpty()) {
			List<Long> inactivatedIds = targets.stream().map(MeetingQuestion::getId).toList();
			log.info("Inactivated {} meeting questions. {}", targets.size(), inactivatedIds);
		}
	}
}
