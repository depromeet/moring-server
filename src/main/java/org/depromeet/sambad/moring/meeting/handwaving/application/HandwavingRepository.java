package org.depromeet.sambad.moring.meeting.handwaving.application;

import org.depromeet.sambad.moring.meeting.handwaving.domain.Handwaving;

public interface HandwavingRepository {

	void save(Handwaving handwaving);

	Handwaving getById(Long handwavingId);
}
