package org.depromeet.sambad.moring.domain.meeting.question.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.SoftAssertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.depromeet.sambad.moring.domain.auth.application.dto.AuthAttributes;
import org.depromeet.sambad.moring.domain.common.domain.Gender;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.domain.meeting.meeting.domain.MeetingCode;
import org.depromeet.sambad.moring.domain.meeting.meeting.presentation.request.MeetingPersistRequest;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MBTI;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMember;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberRole;
import org.depromeet.sambad.moring.domain.meeting.member.presentation.request.MeetingMemberPersistRequest;
import org.depromeet.sambad.moring.domain.user.domain.LoginProvider;
import org.depromeet.sambad.moring.domain.user.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class MeetingQuestionTest {

	@Test
	void 다음_모임_질문을_생성할_수_있다() {
		// given
		Meeting meeting = createMeeting();
		MeetingMember meetingMember = createMeetingMember(meeting);

		// when
		MeetingQuestion nextMeetingQuestion = MeetingQuestion.createNextMeetingQuestion(meeting, meetingMember,
			LocalDateTime.now(), 1);

		// then
		assertSoftly(softly -> {
			assertThat(nextMeetingQuestion.getMeeting()).isNotNull();
			assertThat(nextMeetingQuestion.getQuestion()).isNull();
			assertThat(nextMeetingQuestion.getTargetMember()).isNotNull();
			assertThat(nextMeetingQuestion.getStatus()).isEqualTo(MeetingQuestionStatus.NOT_STARTED);
			assertThat(nextMeetingQuestion.getTotalMemberCount()).isGreaterThan(0);
		});
	}

	private Meeting createMeeting() {
		return Meeting.of(
			new MeetingPersistRequest("나현이와아이들", List.of(1L)), MeetingCode.from("777777")
		);
	}

	private MeetingMember createMeetingMember(Meeting meeting) {
		User user = User.from(null, AuthAttributes.of(LoginProvider.test.name(), null));
		MeetingMemberPersistRequest meetingMemberPersistRequest = new MeetingMemberPersistRequest(
			MeetingMemberRole.MEMBER, "나현", Gender.FEMALE, LocalDate.of(1999, 7, 9),
			"개발자", "인천", List.of(1L), MBTI.ENFJ, "반갑다 친구들아");

		return MeetingMember.createMemberWith(meeting, user, meetingMemberPersistRequest);
	}
}