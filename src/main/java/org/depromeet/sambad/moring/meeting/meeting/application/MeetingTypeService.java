package org.depromeet.sambad.moring.meeting.meeting.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.meeting.domain.MeetingType;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MeetingTypeService {

	private final MeetingTypeRepository meetingTypeRepository;

	// TODO: ADD Cache based redis
	public List<MeetingType> getMeetingTypes() {
		return meetingTypeRepository.findAll();
	}
}
