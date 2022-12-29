package com.codestates.sof.domain.question.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.codestates.sof.domain.common.Vote;
import com.codestates.sof.domain.common.VoteType;
import com.codestates.sof.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
	@UniqueConstraint(name = "unq_question_vote_member_question", columnNames = {"member_id", "question_id"})
})
public class QuestionVote extends Vote {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_vote_id", nullable = false)
	private Long questionVoteId;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "question_id", nullable = false, updatable = false)
	private Question question;

	public QuestionVote(Question question, Member member, VoteType type) {
		super(member, type);
		this.question = question;
	}
}
