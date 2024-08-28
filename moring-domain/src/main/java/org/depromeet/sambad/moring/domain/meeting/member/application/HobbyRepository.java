package org.depromeet.sambad.moring.domain.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.domain.meeting.member.domain.Hobby;

public interface HobbyRepository {

	List<Hobby> findAll();

	List<Hobby> findByIdIn(List<Long> ids);
}
