package org.depromeet.sambad.moring.meeting.question.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestionStatus;
import org.depromeet.sambad.moring.meeting.question.presentation.response.FullInactiveMeetingQuestionListResponse;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MeetingQuestionStatisticsDetail;
import org.depromeet.sambad.moring.meeting.question.presentation.response.MostInactiveMeetingQuestionListResponse;
import org.springframework.data.domain.Pageable;

public interface MeetingQuestionRepository {

	MeetingQuestion save(MeetingQuestion meetingQuestion);

	Optional<MeetingQuestion> findNextQuestion(Long meetingId);

	boolean existsByQuestion(Long meetingId, Long questionId);

	// 현재 진행 중인 모임 질문을 조회한다.
	Optional<MeetingQuestion> findCurrentOne(Long meetingId);

	// 질문이 등록된 현재 진행 중인 모임 질문을 조회한다.
	Optional<MeetingQuestion> findCurrentActiveOne(Long meetingId);

	MostInactiveMeetingQuestionListResponse findTopInactiveList(Long meetingId);

	FullInactiveMeetingQuestionListResponse findFullInactiveList(Long meetingId, Pageable pageable);

	Optional<MeetingQuestion> findByMeetingIdAndMeetingQuestionId(Long meetingId, Long meetingQuestionId);

	List<MeetingQuestionStatisticsDetail> findStatistics(Long meetingQuestionId);

	List<MeetingMember> findMeetingMembersByMeetingQuestionId(Long meetingQuestionId);

	List<MeetingQuestion> findAllByStatusAndExpiredAtBefore(MeetingQuestionStatus status, LocalDateTime now);

	Optional<MeetingQuestion> findFirstByMeetingIdAndStatus(Long meetingId, MeetingQuestionStatus status);

	boolean isAnswered(Long meetingQuestionId, Long meetingMemberId);
}
