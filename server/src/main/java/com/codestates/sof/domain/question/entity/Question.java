package com.codestates.sof.domain.question.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.codestates.sof.domain.tag.entity.Tag;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@Table(indexes = {
	@Index(name = "idx_question_title", columnList = "title"),
	@Index(name = "idx_question_created_at", columnList = "created_at"),
	@Index(name = "idx_question_view_count", columnList = "view_count")
})
public class Question extends BaseEntity {
	@Id
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long questionId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", updatable = false)
	private Member member;

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

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuestionComment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuestionTag> tags = new ArrayList<>();

	public Question(Member writer, String title, String content) {
		this(writer, title, content, new ArrayList<>());
	}

	public Question(Member writer, String title, String content, List<Tag> tags) {
		this.member = writer;
		this.title = title;
		this.content = content;
		this.tags = new ArrayList<>();
		tags.forEach(this::addTag);
	}

	public boolean isWrittenBy(Member member) {
		return Objects.equals(this.member, member);
	}

	public boolean hasAlreadyVoted() {
		return false;
	}

	public void update(Question newQuestion, List<Tag> tags) {
		if (newQuestion.getTitle() != null)
			title = newQuestion.getTitle();

		if (newQuestion.getContent() != null)
			content = newQuestion.getContent();

		update(tags);

		// comment
		// bookmark
		// adopted Answer
	}


	// *** Tag ***
	public void increaseTaggedCountForAllTags() {
		tags.forEach(tag -> tag.getTag().increaseTaggedCount());
	}

	public void increaseViewCount() {
		this.viewCount++;
	}

	public void update(List<Tag> newTags) {
		List<Tag> oldTags = tags.stream().map(QuestionTag::getTag).collect(Collectors.toList());

		// 1. 필요없는걸 지운다.
		new ArrayList<>(tags).stream()
			.filter(tag -> !newTags.contains(tag.getTag()))
			.forEach(tag -> {
				tag.getTag().decreaseTaggedCount();
				tags.remove(tag);
			});

		// 2. 필요한걸 더한다.
		newTags.stream()
			.filter(tag -> !oldTags.contains(tag))
			.forEach(tag -> {
				Tag newTag = addTag(tag);
				newTag.increaseTaggedCount();
			});
	}

	public void addTag(String tagName) {
		Tag tag = new Tag(tagName.toLowerCase());
		tags.add(new QuestionTag(tag, this));
	}

	public Tag addTag(Tag tag) {
		tags.add(new QuestionTag(tag, this));
		return tag;
	}

	public List<String> getTagNames() {
		return tags.stream().map(QuestionTag::getTagName).collect(Collectors.toList());
	}

	// *** Comment ***

	public QuestionComment addComment(QuestionComment comment) {
		comments.add(comment);
		return comment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Question question = (Question)o;
		return Objects.equals(questionId, question.questionId);
	}

	@Override
	public int hashCode() {
		return questionId != null ? questionId.hashCode() : 0;
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
