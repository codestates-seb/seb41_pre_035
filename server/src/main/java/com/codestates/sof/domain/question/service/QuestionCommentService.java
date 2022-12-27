package com.codestates.sof.domain.question.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.repository.QuestionCommentRepository;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionCommentService {
	private final QuestionCommentRepository commentRepository;
	private final QuestionService questionService;
	private final MemberService memberService;

	@Transactional
	public QuestionComment comment(long questionId, QuestionComment comment) {
		Member member = memberService.findMember(comment.getMember().getMemberId());
		Question question = questionService.findByIdWithoutIncreasingViewCount(questionId);

		comment.setMember(member);

		return question.addComment(comment);
	}

	@Transactional
	public QuestionComment modify(long questionId, long modifierId, QuestionComment comment) {
		QuestionComment existsComment = getExistsComment(comment);

		if (!existsComment.isWrittenBy(modifierId)) {
			throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING_COMMENT);
		}

		if (!existsComment.getQuestion().getQuestionId().equals(questionId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_QUESTION);
		}

		existsComment.setContent(comment.getContent());

		return existsComment;
	}

	@Transactional
	public void delete(long memberId, long questionId, long commentId) {
		QuestionComment comment = getExistsComment(commentId);

		if (!comment.isWrittenBy(memberId)) {
			throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING_COMMENT);
		}

		commentRepository.delete(comment);
	}

	private QuestionComment getExistsComment(QuestionComment comment) {
		if (Objects.isNull(comment.getQuestionCommentId()))
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_COMMENT);

		return getExistsComment(comment.getQuestionCommentId());
	}

	private QuestionComment getExistsComment(long commentId) {
		return commentRepository.findById(commentId)
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_COMMENT));
	}
}
