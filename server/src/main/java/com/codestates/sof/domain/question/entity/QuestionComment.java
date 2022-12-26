package com.codestates.sof.domain.question.entity;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.codestates.sof.domain.common.BaseEntity;
import com.codestates.sof.domain.member.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
	indexes = {
		@Index(name = "idx_qt_cmt_member_creation", columnList = "member_id, created_at"),
		@Index(name = "idx_qt_cmt_question_creation", columnList = "question_id, created_at")
	})
public class QuestionComment extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionCommentId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, updatable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id", nullable = false, updatable = false)
	private Question question;

	private String content;

	public QuestionComment(Member member, Question question, String content) {
		this.member = member;
		this.content = content;
		this.question = question;
	}

	public boolean isItWriter(Long memberId) {
		return Objects.equals(memberId, member.getMemberId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		return Objects.equals(questionCommentId, ((QuestionComment)o).questionCommentId);
	}

	@Override
	public int hashCode() {
		return questionCommentId != null ? questionCommentId.hashCode() : 0;
	}

	public static final class Builder {
		private Long questionCommentId;
		private Member member;
		private Question question;
		private String content;

		private Builder() {
		}

		public static Builder aQuestionComment() {
			return new Builder();
		}

		public Builder questionCommentId(Long questionCommentId) {
			this.questionCommentId = questionCommentId;
			return this;
		}

		public Builder member(Member member) {
			this.member = member;
			return this;
		}

		public Builder question(Question question) {
			this.question = question;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public QuestionComment build() {
			QuestionComment questionComment = new QuestionComment(member, question, content);
			questionComment.questionCommentId = this.questionCommentId;
			return questionComment;
		}
	}
}