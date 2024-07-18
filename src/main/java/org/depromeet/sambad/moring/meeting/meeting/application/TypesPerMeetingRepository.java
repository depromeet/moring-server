package org.depromeet.sambad.moring.meeting.meeting.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.domain.TypesPerMeeting;

public interface TypesPerMeetingRepository {

	void saveAll(List<TypesPerMeeting> types);
}
