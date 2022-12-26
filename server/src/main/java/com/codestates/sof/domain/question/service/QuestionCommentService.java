package com.codestates.sof.domain.question.service;

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

		if (!existsComment.isItWriter(modifierId)) {
			throw new BusinessLogicException(ExceptionCode.NO_PERMISSION_EDITING_COMMENT);
		}

		if (!existsComment.getQuestion().getQuestionId().equals(questionId)) {
			throw new BusinessLogicException(ExceptionCode.NOT_FOUND_QUESTION);
		}

		existsComment.setContent(comment.getContent());

		return existsComment;
	}

	private QuestionComment getExistsComment(QuestionComment comment) {
		return commentRepository.findById(comment.getQuestionCommentId())
			.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_COMMENT));
	}
}
