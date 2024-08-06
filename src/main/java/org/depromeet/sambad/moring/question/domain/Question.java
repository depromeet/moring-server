package org.depromeet.sambad.moring.question.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.common.domain.BaseTimeEntity;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	private static final int MIN_ANSWER_COUNT = 2;
	private static final int MAX_ANSWER_COUNT = 9;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_image_file_id")
	private FileEntity questionImageFile;

	private String title;

	@Enumerated(EnumType.STRING)
	private QuestionType questionType;

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
	private List<MeetingQuestion> meetingQuestions = new ArrayList<>();

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Answer> answers = new ArrayList<>();

	@Builder
	public Question(String title, FileEntity questionImageFile, QuestionType questionType,
		List<String> answerContents) {
		QuestionType.validateAnswerCount(questionType, answerContents.size());
		this.questionType = questionType;
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

	public void addMeetingQuestion(MeetingQuestion meetingQuestion) {
		this.meetingQuestions.add(meetingQuestion);
	}

	public String getQuestionImageUrl() {
		return Optional.ofNullable(questionImageFile)
			.map(FileEntity::getPhysicalPath)
			.orElse(null);
	}

	public QuestionType getQuestionType() {
		return questionType;
	}
}
