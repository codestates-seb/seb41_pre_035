package com.codestates.sof.domain.question.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.repository.QuestionCommentRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QuestionCommentService {
	private final QuestionCommentRepository commentRepository;
	private final QuestionService questionService;

	@Transactional
	public QuestionComment comment(Member member, long questionId, String content) {
		Question question = questionService.findByIdWithoutIncreasingViewCount(questionId);
		QuestionComment comment = new QuestionComment(member, question, content);

		return question.addComment(comment);
	}

	@Transactional
	public QuestionComment modify(Member member, long questionId, long commentId, String content) {
		QuestionComment comment = getVerifiedComment(commentId, questionId, member);

		comment.modify(content);

		return comment;
	}

	@Transactional(readOnly = true)
	public Page<QuestionComment> findAll(long questionId, int page, int size) {
		Question question = questionService.findByIdWithoutIncreasingViewCount(questionId);
		PageRequest pageRequest = PageRequest.of(page, size, Sort.by("createdAt").descending());
		return commentRepository.findAllByQuestion(question, pageRequest);
	}

	@Transactional
	public void delete(Member member, long questionId, long commentId) {
		QuestionComment comment = getVerifiedComment(commentId, questionId, member);

		commentRepository.delete(comment);
	}

	private QuestionComment getVerifiedComment(long commentId, long questionId, Member member) {
		QuestionComment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_COMMENT));

		if (!comment.isWrittenBy(member)) {
			throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING_COMMENT);
		}

		if (!comment.isGroupOf(questionId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_QUESTION);
		}

		return comment;
	}
}
