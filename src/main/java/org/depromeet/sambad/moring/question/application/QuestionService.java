package org.depromeet.sambad.moring.question.application;

import java.util.List;
import java.util.Random;

import org.depromeet.sambad.moring.file.application.FileService;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMemberValidator;
import org.depromeet.sambad.moring.question.domain.Question;
import org.depromeet.sambad.moring.question.presentation.exception.NotFoundAvailableQuestionException;
import org.depromeet.sambad.moring.question.presentation.exception.NotFoundQuestionException;
import org.depromeet.sambad.moring.question.presentation.request.QuestionRequest;
import org.depromeet.sambad.moring.question.presentation.response.QuestionListResponse;
import org.depromeet.sambad.moring.question.presentation.response.QuestionResponse;
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