package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingAnswerRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingAnswerRepositoryImpl implements MeetingAnswerRepository {

	private final MeetingMemberAnswerJpaRepository meetingMemberAnswerJpaRepository;
}
