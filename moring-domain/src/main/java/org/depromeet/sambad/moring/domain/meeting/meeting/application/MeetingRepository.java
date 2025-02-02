package org.depromeet.sambad.moring.domain.meeting.meeting.application;

import java.util.Optional;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingCode;

public interface MeetingRepository {

	Optional<Meeting> findById(Long id);

	void save(Meeting meeting);

	Optional<Meeting> findByCode(MeetingCode code);
}
