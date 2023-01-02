package com.codestates.sof.domain.answer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.codestates.sof.domain.common.BaseEntity;
import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Question;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Answer extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long answerId;

	@Column(columnDefinition = "MEDIUMTEXT", nullable = false)
	private String content;

	@Column(nullable = false)
	private int voteCount = 0;

	@Column(name = "is_accepted", nullable = false)
	private boolean isAccepted;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false)
	private Question question;

	@OneToMany(
		mappedBy = "answer",
		cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
		orphanRemoval = true)
	private List<AnswerComment> comments = new ArrayList<>();

	@OneToMany(
		mappedBy = "answer",
		cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
		orphanRemoval = true)
	private List<AnswerVote> votes = new ArrayList<>();

	public Answer(Question question, Member member, String content) {
		this.question = question;
		this.member = member;
		this.content = content;
		this.voteCount = 0;
	}

	public boolean isGroupOf(Question question) {
		return this.question.equals(question);
	}
}
