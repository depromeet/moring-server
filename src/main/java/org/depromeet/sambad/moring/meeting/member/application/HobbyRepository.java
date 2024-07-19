package org.depromeet.sambad.moring.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.Hobby;

public interface HobbyRepository {

	List<Hobby> findAll();

	List<Hobby> findByIdIn(List<Long> ids);
}
