package org.depromeet.sambad.moring.meeting.member.application;

import java.util.List;

import org.depromeet.sambad.moring.meeting.member.domain.Hobby;
import org.depromeet.sambad.moring.meeting.member.presentation.response.HobbyResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HobbyService {

	private final HobbyRepository hobbyRepository;

	public HobbyResponse getHobbies() {
		List<Hobby> hobbies = hobbyRepository.findAll();
		return HobbyResponse.from(hobbies);
	}
}
