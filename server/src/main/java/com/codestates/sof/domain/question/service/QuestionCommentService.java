package com.codestates.sof.domain.question.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.member.service.MemberService;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.entity.QuestionComment;
import com.codestates.sof.domain.question.repository.QuestionCommentRepository;

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
		Question question = questionService.findById(questionId);

		comment.setMember(member);

		return question.addComment(comment);
	}
}
