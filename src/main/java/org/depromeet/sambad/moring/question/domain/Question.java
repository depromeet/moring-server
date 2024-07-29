package org.depromeet.sambad.moring.question.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;
import org.depromeet.sambad.moring.question.presentation.exception.AnswerCountOutOfRangeException;

import jakarta.persistence.CascadeType;
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
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_image_file_id")
	private FileEntity questionImageFile;

	private String title;

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
	private List<MeetingQuestion> meetingQuestions = new ArrayList<>();

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	public void addMeetingQuestion(MeetingQuestion meetingQuestion) {
		this.meetingQuestions.add(meetingQuestion);
	}

	public String getQuestionImageUrl() {
		return Optional.ofNullable(questionImageFile)
			.map(FileEntity::getPhysicalPath)
			.orElse(null);
	}

	@Builder
	public Question(String title, FileEntity questionImageFile, List<String> answerContents) {
		checkAnswerContents(answerContents);

		this.title = title;
		this.questionImageFile = questionImageFile;

		answerContents.forEach(answerContent -> answers.add(Answer.builder()
			.question(this)
			.content(answerContent)
			.build()));

	}

	public void addAnswer(Answer answer) {
		answers.add(answer);
	}

	private void checkAnswerContents(List<String> answerContents) {
		if (answerContents.size() < 2 || answerContents.size() > 16) {
			throw new AnswerCountOutOfRangeException();
		}
	}
}
