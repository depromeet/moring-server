package org.depromeet.sambad.moring.user.application;

import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.user.domain.User;
import org.depromeet.sambad.moring.user.domain.UserRepository;
import org.depromeet.sambad.moring.user.presentation.exception.NotFoundUserException;
import org.depromeet.sambad.moring.user.presentation.response.OnboardingResponse;
import org.depromeet.sambad.moring.user.presentation.response.UserResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

	private final UserRepository userRepository;
	private final MeetingMemberValidator meetingMemberValidator;

	public UserResponse findByUserId(Long userId) {
		return userRepository.findById(userId)
			.map(UserResponse::from)
			.orElseThrow(NotFoundUserException::new);
	}

	@Transactional
	public OnboardingResponse completeOnboarding(Long userId) {
		User user = userRepository.findById(userId)
			.orElseThrow(NotFoundUserException::new);

		user.completeOnboarding();

		return OnboardingResponse.from(user);
	}

	@Transactional
	public void updateLastMeeting(Long userId, Long meetingId) {
		User user = userRepository.findById(userId)
			.orElseThrow(NotFoundUserException::new);

		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		user.updateLastAccessedMeeting(meetingId);
	}
}
