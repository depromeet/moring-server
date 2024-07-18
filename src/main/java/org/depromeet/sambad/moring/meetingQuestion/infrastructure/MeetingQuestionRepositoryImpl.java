package org.depromeet.sambad.moring.meetingQuestion.infrastructure;

import org.depromeet.sambad.moring.meetingQuestion.application.MeetingQuestionRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingQuestionRepositoryImpl implements MeetingQuestionRepository {

	private final MeetingQuestionJpaRepository meetingQuestionJpaRepository;
}
