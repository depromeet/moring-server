package org.depromeet.sambad.moring.question.application;

import java.util.List;
import java.util.Random;

import org.depromeet.sambad.moring.answer.domain.Answer;
import org.depromeet.sambad.moring.file.application.FileService;
import org.depromeet.sambad.moring.file.domain.FileEntity;
import org.depromeet.sambad.moring.meeting.meeting.domain.Meeting;
import org.depromeet.sambad.moring.meeting.member.application.MeetingMemberService;
import org.depromeet.sambad.moring.meeting.member.domain.MeetingMember;
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

	private final MeetingMemberService meetingMemberService;

	private final FileService fileService;

	public Question getById(Long id) {
		return questionRepository.findById(id)
			.orElseThrow(NotFoundQuestionException::new);
	}

	public QuestionListResponse findQuestions(Long userId, Long meetingId, int page, int size) {
		MeetingMember loginMember = meetingMemberService.getByUserIdAndMeetingId(userId, meetingId);
		Meeting meeting = loginMember.getMeeting();
		return questionRepository.findQuestionsByMeeting(meeting.getId(), PageRequest.of(page, size));
	}

	public QuestionResponse getRandomOne(List<Long> excludeQuestionIds) {
		List<Question> questions = questionRepository.findAllByNotInQuestionIds(excludeQuestionIds);

		if (questions.isEmpty()) {
			throw new NotFoundAvailableQuestionException();
		}

		Question randomQuestion = questions.get(new Random().nextInt(questions.size()));
		return QuestionResponse.from(randomQuestion);
	}

	@Transactional
	public void saveQuestion(QuestionRequest questionRequest) {
		FileEntity image = fileService.uploadAndSave(questionRequest.questionImageUrl());
		Question question = Question.builder()
			.title(questionRequest.title())
			.questionImageFile(image)
			.build();
		questionRepository.save(question);
	}
}
