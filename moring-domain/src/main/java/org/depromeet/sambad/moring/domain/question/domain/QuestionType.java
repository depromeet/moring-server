package org.depromeet.sambad.moring.domain.question.domain;

import org.depromeet.sambad.moring.domain.question.presentation.exception.AnswerCountOutOfRangeException;

public enum QuestionType {
	SINGLE_CHOICE, MULTIPLE_SHORT_CHOICE, MULTIPLE_DESCRIPTIVE_CHOICE;

	private static final int MIN_ANSWER_COUNT = 1;
	private static final int MULTI_CHOICE_MAX_ANSWER_COUNT = 9;

	public static void validateAnswerCount(QuestionType questionType, int answerCount) {
		if (SINGLE_CHOICE.equals(questionType)) {
			if (answerCount != MIN_ANSWER_COUNT) {
				throw new AnswerCountOutOfRangeException();
			}
			return;
		}
		if (answerCount < MIN_ANSWER_COUNT || answerCount > MULTI_CHOICE_MAX_ANSWER_COUNT) {
			throw new AnswerCountOutOfRangeException();
		}
	}
}
