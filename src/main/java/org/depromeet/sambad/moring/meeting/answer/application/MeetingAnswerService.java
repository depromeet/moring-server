package org.depromeet.sambad.moring.meeting.answer.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeetingAnswerService {

	private final MeetingAnswerRepository meetingAnswerRepository;
}
