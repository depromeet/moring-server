package org.depromeet.sambad.moring.question.domain;

public enum QuestionType {
	SINGLE_CHOICE, MULTIPLE_CHOICE;

	private static final int SINGLE_CHOICE_ANSWER_COUNT = 2;

	public static QuestionType getQuestionType(int answerSize) {
		if (answerSize <= SINGLE_CHOICE_ANSWER_COUNT) {
			return SINGLE_CHOICE;
		}
		return MULTIPLE_CHOICE;
	}
}
