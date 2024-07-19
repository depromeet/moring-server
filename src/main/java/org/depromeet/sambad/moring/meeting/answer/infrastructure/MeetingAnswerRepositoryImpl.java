package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerRepository;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingAnswerRepositoryImpl implements MeetingAnswerRepository {

	private final MeetingAnswerJpaRepository meetingAnswerJpaRepository;

	@Override
	public void save(MeetingAnswer meetingAnswer) {
		meetingAnswerJpaRepository.save(meetingAnswer);
	}

	@Override
	public boolean existsByMeetingMember(Long meetingQuestionId, Long meetingMemberId) {
		return meetingAnswerJpaRepository.existsByMeetingQuestionIdAndMeetingMemberId(meetingQuestionId,
			meetingMemberId);
	}
}