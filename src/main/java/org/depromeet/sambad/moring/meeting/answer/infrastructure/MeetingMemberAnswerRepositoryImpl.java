package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import org.depromeet.sambad.moring.meeting.answer.application.MeetingMemberAnswerRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingMemberAnswerRepositoryImpl implements MeetingMemberAnswerRepository {

	private final MeetingMemberAnswerJpaRepository meetingMemberAnswerJpaRepository;
}
