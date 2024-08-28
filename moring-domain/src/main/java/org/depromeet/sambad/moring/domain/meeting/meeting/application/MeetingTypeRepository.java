package org.depromeet.sambad.moring.domain.meeting.meeting.application;

import java.util.List;
import java.util.Set;

import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingType;

public interface MeetingTypeRepository {

	List<MeetingType> findAll();

	Set<MeetingType> findByIdIn(List<Long> ids);
}
