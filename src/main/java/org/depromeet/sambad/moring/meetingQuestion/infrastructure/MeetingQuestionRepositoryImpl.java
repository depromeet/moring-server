package org.depromeet.sambad.moring.meetingQuestion.infrastructure;

import org.depromeet.sambad.moring.meetingQuestion.application.MeetingQuestionRepository;
import org.depromeet.sambad.moring.meetingQuestion.domain.MeetingQuestion;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingQuestionRepositoryImpl implements MeetingQuestionRepository {

	private final MeetingQuestionJpaRepository meetingQuestionJpaRepository;

	@Override
	public void save(MeetingQuestion meetingQuestion) {
		meetingQuestionJpaRepository.save(meetingQuestion);
	}

	@Override
	public boolean existsByQuestion(Long meetingId, Long questionId) {
		return meetingQuestionJpaRepository.existsByMeetingIdAndQuestionId(meetingId, questionId);
	}
}
