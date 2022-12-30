package com.codestates.sof.domain.answer.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.sof.domain.common.Vote;
import com.codestates.sof.domain.common.VoteType;
import com.codestates.sof.domain.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AnswerVote extends Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long answerVoteId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "answer_id", nullable = false, updatable = false)
	private Answer answer;

	public AnswerVote(Answer answer, Member member, VoteType type) {
		super(member, type);
		this.answer = answer;
	}
}
