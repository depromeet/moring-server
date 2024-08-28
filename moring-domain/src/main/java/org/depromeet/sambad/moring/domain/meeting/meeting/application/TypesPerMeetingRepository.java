package org.depromeet.sambad.moring.domain.meeting.meeting.application;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.TypesPerMeeting;

public interface TypesPerMeetingRepository {

	void saveAll(List<TypesPerMeeting> types);
}
