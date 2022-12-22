package com.codestates.sof.domain.question.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import com.codestates.sof.domain.common.BaseEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
	@Index(name = "idx_question_title", columnList = "title"),
	@Index(name = "idx_question_created_at", columnList = "created_at")
})
public class Question extends BaseEntity {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@Column(name = "member_id", nullable = false, updatable = false, unique = true)
	private Long writerId;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
	private String content;

	@Column(name = "view_count", nullable = false)
	private int viewCount;

	@Column(name = "vote_count", nullable = false)
	private int voteCount;

	public Question(Long writerId, String title, String content) {
		this.writerId = writerId;
		this.title = title;
		this.content = content;
		this.viewCount = this.voteCount = 0;
	}

	public void postWrote() {
		// TODO
	}

	public boolean isItWriter() {
		return false;
	}

	public boolean hasAlreadyVoted() {
		return false;
	}

	public static final class Builder {
		private Long questionId;
		private Long writerId;
		private String title;
		private String content;
		private int viewCount;
		private int voteCount;

		private Builder() {}

		public static Builder aQuestion() {
			return new Builder();
		}

		public Builder questionId(Long questionId) {
			this.questionId = questionId;
			return this;
		}

		public Builder writerId(Long writerId) {
			this.writerId = writerId;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder content(String content) {
			this.content = content;
			return this;
		}

		public Builder viewCount(int viewCount) {
			this.viewCount = viewCount;
			return this;
		}

		public Builder voteCount(int voteCount) {
			this.voteCount = voteCount;
			return this;
		}

		public Question build() {
			Question question = new Question(writerId, title, content);
			question.questionId = this.questionId;
			question.viewCount = this.viewCount;
			question.voteCount = this.voteCount;
			return question;
		}
	}
}
