package org.depromeet.sambad.moring.question.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.question.domain.MeetingQuestion;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_image_id")
	private FileEntity questionImage;

	private String title;

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
	private List<MeetingQuestion> meetingQuestions = new ArrayList<>();

	@OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
	private List<Answer> answers = new ArrayList<>();

	public String getQuestionImageUrl() {
		return Optional.ofNullable(questionImage)
			.map(FileEntity::getPhysicalPath)
			.orElse(null);
	}
}
