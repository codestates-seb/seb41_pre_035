package com.codestates.sof.domain.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.repository.QuestionRepository;
import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.service.TagService;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionService {
	private final QuestionRepository questionRepository;
	private final TagService tagService;

	@Transactional
	public Question write(Question question) {
		replaceTagNameToTag(question);
		question = save(question);
		question.increaseTaggedCountForAllTags();
		return question;
	}

	@Transactional
	public Question findById(Long questionId) {
		Question question = findExistsQuestion(questionId);
		question.increaseViewCount();
		return question;
	}

	@Transactional
	public Question patch(Long questionId, Long memberId, Question newQuestion) {
		Question question = findExistsQuestion(questionId);

		if (!question.isWrittenBy(memberId)) {
			throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING_QUESTION);
		}

		question.update(newQuestion);
		replaceTagNameToTag(question);

		return question;
	}

	public void delete(Long memberId, Long questionId) {
		Question question = findExistsQuestion(questionId);

		if (!question.isWrittenBy(memberId))
			throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING_QUESTION);

		// TODO (AUTH, COMMENT)

		questionRepository.delete(question);
	}

	private void replaceTagNameToTag(Question question) {
		question.getTags()
			.forEach(questionTag -> {
				Tag tag = tagService.findBy(questionTag.getTag().getName());
				questionTag.setTag(tag);
				questionTag.setQuestion(question);
			});
	}

	private Question findExistsQuestion(Long questionId) {
		return questionRepository.findByQuestionId(questionId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_QUESTION));
	}

	private Question save(Question question) {
		return questionRepository.save(question);
	}
}
