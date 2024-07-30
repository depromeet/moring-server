package org.depromeet.sambad.moring.answer.domain;

import java.util.ArrayList;
import java.util.List;

import org.depromeet.sambad.moring.answer.presentation.exception.InvalidAnswerContentException;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.meeting.answer.domain.MeetingAnswer;
import org.depromeet.sambad.moring.question.domain.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "answer_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	@OneToMany(mappedBy = "answer", fetch = FetchType.LAZY)
	private List<MeetingAnswer> meetingAnswers = new ArrayList<>();

	private String content;

	@Builder
	public Answer(Question question, String content) {
		validateIsNullOrBlank(content);
		this.question = question;
		this.content = content;
		question.addAnswer(this);
	}

	private void validateIsNullOrBlank(String content) {
		if (content == null || content.isBlank()) {
			throw new InvalidAnswerContentException();
		}
	}
}