package com.codestates.sof.domain.question.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.codestates.sof.domain.answer.entity.Answer;
import com.codestates.sof.domain.common.BaseEntity;
import com.codestates.sof.domain.member.entity.Member;

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
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "question_id", nullable = false)
	private Long questionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", updatable = false)
	private Member writer;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content", nullable = false, columnDefinition = "MEDIUMTEXT")
	private String content;

	@Column(name = "view_count", nullable = false)
	private int viewCount;

	@Column(name = "vote_count", nullable = false)
	private int voteCount;

	@Column(name = "has_adopted_answer")
	private boolean hasAdoptedAnswer;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<Answer> answers = new ArrayList<>();

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
	private List<QuestionTag> tags = new ArrayList<>();

	public Question(Member writer, String title, String content) {
		this(writer, title, content, new ArrayList<>());
	}

	public Question(Member writer, String title, String content, List<QuestionTag> tags) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		this.tags = tags;
	}

	public void afterWrote() {
		// TODO
	}

	public void afterFound() {
		this.viewCount++;
		// TODO
	}

	public boolean isItWriter() {
		return false;
	}

	public boolean hasAlreadyVoted() {
		return false;
	}

	public void update(Question newQuestion) {
		if (newQuestion.getTitle() != null)
			title = newQuestion.getTitle();

		if (newQuestion.getContent() != null)
			content = newQuestion.getContent();
	}

	public void setTags(List<QuestionTag> tags) {
		this.tags = tags;
	}

	public static final class Builder {
		private Long questionId;
		private Member writer;
		private String title;
		private String content;
		private int viewCount;
		private int voteCount;
		private List<QuestionTag> tags;

		private Builder() {
		}

		public static Builder aQuestion() {
			return new Builder();
		}

		public Builder questionId(Long questionId) {
			this.questionId = questionId;
			return this;
		}

		public Builder writer(Member writer) {
			this.writer = writer;
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

		public Builder tags(List<QuestionTag> tags) {
			this.tags = tags;
			return this;
		}

		public Question build() {
			Question question = new Question(writer, title, content);
			question.questionId = this.questionId;
			question.viewCount = this.viewCount;
			question.voteCount = this.voteCount;
			question.tags = this.tags;
			return question;
		}
	}
}
