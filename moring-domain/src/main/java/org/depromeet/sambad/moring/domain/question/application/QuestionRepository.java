package org.depromeet.sambad.moring.domain.question.application;

import java.util.List;
import java.util.Optional;

import org.depromeet.sambad.moring.domain.question.domain.Question;
import org.depromeet.sambad.moring.domain.question.presentation.response.QuestionListResponse;
import org.springframework.data.domain.Pageable;

public interface QuestionRepository {

	Optional<Question> findById(Long id);

	QuestionListResponse findQuestionsByMeetingId(Long meetingId, Pageable pageable);

	List<Question> findAllByNotInQuestionIds(List<Long> excludeQuestionIds);

	void save(Question question);
}
