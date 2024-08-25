package org.depromeet.sambad.moring.meeting.answer.infrastructure;

import java.util.List;

import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MeetingAnswerJpaRepository extends JpaRepository<MeetingAnswer, Long> {

	boolean existsByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId);

	List<MeetingAnswer> findByMeetingQuestionIdAndMeetingMemberId(Long meetingQuestionId, Long meetingMemberId);

	@Modifying
	@Query("update MeetingAnswer a set a.isHidden = true where a.meetingMember.id = :meetingMemberId")
	void updateAllHiddenByMeetingMemberId(Long meetingMemberId);

	@Modifying
	@Query("update MeetingAnswer a "
		+ "set a.isHidden = true "
		+ "where a.meetingMember.id = :meetingMemberId and a.meetingQuestion.id not in (:activeMeetingQuestionIds)")
	void updateManyHiddenByMeetingMemberIdAndMeetingQuestionId(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds);

	@Modifying
	@Query("update MeetingAnswer a "
		+ "set a.isHidden = false "
		+ "where a.meetingMember.id = :meetingMemberId and a.meetingQuestion.id in :activeMeetingQuestionIds")
	void updateManyActivateByMeetingMemberIdAndMeetingQuestionId(Long meetingMemberId,
		List<Long> activeMeetingQuestionIds);
}