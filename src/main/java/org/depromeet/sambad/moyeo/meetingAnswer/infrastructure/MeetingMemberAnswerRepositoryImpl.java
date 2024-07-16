package org.depromeet.sambad.moyeo.meetingAnswer.infrastructure;

import org.depromeet.sambad.moyeo.meetingAnswer.application.MeetingMemberAnswerRepository;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MeetingMemberAnswerRepositoryImpl implements MeetingMemberAnswerRepository {

	private final MeetingMemberAnswerJpaRepository meetingMemberAnswerJpaRepository;
}
