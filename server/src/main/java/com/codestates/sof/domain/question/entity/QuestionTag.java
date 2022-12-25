package com.codestates.sof.domain.question.entity;

import java.util.Objects;

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

import com.codestates.sof.domain.tag.entity.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(
	uniqueConstraints = @UniqueConstraint(name = "unq_question_tag", columnNames = {"tag_id", "question_id"})
)
public class QuestionTag {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_tag_id")
	private Long questionTagId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tag_id")
	private Tag tag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

	public QuestionTag(Tag tag, Question question) {
		this.tag = tag;
		this.question = question;
	}

	public String getTagName() {
		return tag.getName();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return Objects.equals(questionTagId, ((QuestionTag)o).questionTagId);
	}

	@Override
	public int hashCode() {
		return questionTagId != null ? questionTagId.hashCode() : 0;
	}
}
