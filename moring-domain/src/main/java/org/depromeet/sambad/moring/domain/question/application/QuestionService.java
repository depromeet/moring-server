package org.depromeet.sambad.moring.domain.question.application;

import java.util.List;
import java.util.Random;

import org.depromeet.sambad.moring.domain.file.application.FileService;
import org.depromeet.sambad.moring.domain.file.domain.FileEntity;
import org.depromeet.sambad.moring.domain.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.domain.question.domain.Question;
import org.depromeet.sambad.moring.domain.question.presentation.exception.NotFoundAvailableQuestionException;
import org.depromeet.sambad.moring.domain.question.presentation.exception.NotFoundQuestionException;
import org.depromeet.sambad.moring.domain.question.presentation.request.QuestionRequest;
import org.depromeet.sambad.moring.domain.question.presentation.response.QuestionListResponse;
import org.depromeet.sambad.moring.domain.question.presentation.response.QuestionResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {

	private final QuestionRepository questionRepository;

	private final FileService fileService;

	private final MeetingMemberValidator meetingMemberValidator;

	@Transactional
	public void saveQuestion(QuestionRequest questionRequest) {
		FileEntity image = fileService.uploadAndSave(questionRequest.questionImageUrl());
		Question question = Question.builder()
			.title(questionRequest.title())
			.questionImageFile(image)
			.questionType(questionRequest.questionType())
			.answerContents(questionRequest.answerContents())
			.build();
		questionRepository.save(question);
	}

	public QuestionListResponse findQuestions(Long userId, Long meetingId, PageRequest pageRequest) {
		meetingMemberValidator.validateUserIsMemberOfMeeting(userId, meetingId);
		return questionRepository.findQuestionsByMeetingId(meetingId, pageRequest);
	}

	public Question getById(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(NotFoundQuestionException::new);
	}

	public QuestionResponse getRandomOne(List<Long> excludeQuestionIds) {
		List<Question> questions = questionRepository.findAllByNotInQuestionIds(excludeQuestionIds);

		if (questions.isEmpty()) {
			throw new NotFoundAvailableQuestionException();
		}

		Question randomQuestion = questions.get(new Random().nextInt(questions.size()));
		return QuestionResponse.from(randomQuestion);
	}
}