package com.codestates.sof.domain.answer.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.answer.entity.AnswerComment;
import com.codestates.sof.domain.answer.repository.AnswerCommentRepository;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.global.error.dto.ExceptionCode;
import com.codestates.sof.global.error.exception.BusinessLogicException;

@Transactional
@Service
public class AnswerCommentService {
	private final AnswerCommentRepository commentRepository;
	private final AnswerService answerService;
	private final MemberService memberService;

	public AnswerCommentService(AnswerCommentRepository commentRepository, AnswerService answerService,
		MemberService memberService) {
		this.commentRepository = commentRepository;
		this.answerService = answerService;
		this.memberService = memberService;
	}

	public AnswerComment createComment(AnswerComment answerComment, long answerId) {
		Member member = memberService.findMember(answerComment.getMember().getMemberId());
		answerComment.setMember(member);

		Answer answer = answerService.findVerifiedAnswer(answerId);
		answerComment.setAnswer(answer);

		AnswerComment savedComment = commentRepository.save(answerComment);

		return savedComment;
	}

	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
	public AnswerComment updateComment(AnswerComment answerComment, long commentId) {
		// 작성자 확인 verify 추가
		AnswerComment findComment = findVerifiedComment(commentId);

		Optional.ofNullable(answerComment.getContent())
			.ifPresent(findComment::setContent);

		return commentRepository.save(findComment);
	}

	public Page<AnswerComment> findComments(int page, int size) {

		return commentRepository.findAll(PageRequest.of(page, size,
			Sort.by("commentId").descending()));
	}

	public void deleteComment(long commentId) {
		AnswerComment comment = findVerifiedComment(commentId);

		commentRepository.delete(comment);
	}

	@Transactional(readOnly = true)
	public AnswerComment findVerifiedComment(long commentId) {
		Optional<AnswerComment> optionalComment = commentRepository.findById(commentId);

		AnswerComment findComment =
			optionalComment.orElseThrow(() -> new BusinessLogicException(ExceptionCode.NOT_FOUND_COMMENT));

		return findComment;
	}
}
