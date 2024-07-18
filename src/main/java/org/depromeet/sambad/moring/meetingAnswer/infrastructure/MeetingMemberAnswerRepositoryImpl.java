package org.depromeet.sambad.moring.meetingAnswer.infrastructure;

import org.depromeet.sambad.moring.meetingAnswer.application.MeetingMemberAnswerRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingMemberAnswerRepositoryImpl implements MeetingMemberAnswerRepository {

	private final MeetingMemberAnswerJpaRepository meetingMemberAnswerJpaRepository;
}
