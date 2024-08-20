package org.depromeet.sambad.moring.meeting.handWaving.application;

import org.depromeet.sambad.moring.meeting.handWaving.domain.HandWaving;

public interface HandWavingRepository {
	void save(HandWaving handWaving);
	HandWaving findById(Long handWavingId);
}
