package com.codestates.sof.domain.answer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codestates.sof.domain.common.BaseEntity;
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

	@Column(nullable = false, updatable = false)
	private Long questionId;

	@Column(nullable = false, updatable = false)
	private Long writerId;

	@Column(columnDefinition = "MEDIUMTEXT", nullable = false)
	private String content;

	@Column(nullable = false)
	private int voteCount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", updatable = false)
	private Question question;

	public Answer(Long questionId, Long writerId, String content) {
		this.questionId = questionId;
		this.writerId = writerId;
		this.content = content;
		this.voteCount = 0;
	}
}
