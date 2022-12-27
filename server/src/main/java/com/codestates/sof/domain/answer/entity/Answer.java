package com.codestates.sof.domain.answer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codestates.sof.domain.common.BaseEntity;

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

	@Column(nullable = true, updatable = false)
	private Long questionId;

	@Column(nullable = false, updatable = false)
	private Long writerId;

	@Column(columnDefinition = "MEDIUMTEXT", nullable = false)
	private String content;

	@Column(nullable = false)
	private int voteCount;

	@OneToMany(
		mappedBy = "answer",
		cascade = {CascadeType.REMOVE, CascadeType.PERSIST},
		orphanRemoval = true)
	private List<AnswerComment> comments = new ArrayList<>();

	public Answer(Long questionId, Long writerId, String content) {
		this.questionId = questionId;
		this.writerId = writerId;
		this.content = content;
		this.voteCount = 0;
	}
}
