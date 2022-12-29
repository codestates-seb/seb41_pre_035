package com.codestates.sof.domain.tag.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.codestates.sof.domain.question.entity.QuestionTag;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tag_id", updatable = false)
	private Long tagId;

	@Column(name = "name", nullable = false, updatable = false, unique = true)
	private String name;

	@Column(name = "description", nullable = false)
	private String description;

	@Column(name = "tagged_count", nullable = false)
	private long taggedCount;

	@OneToMany(mappedBy = "tag")
	private List<QuestionTag> questions = new ArrayList<>();

	public Tag(String name) {
		this.name = name;
	}

	public Tag(String name, String description) {
		this.name = name;
		this.description = description;
	}

	public void increaseTaggedCount() {
		this.taggedCount++;
	}

	public void decreaseTaggedCount() {
		this.taggedCount--;
	}

	public void beforeSave() {
		this.name = name.toLowerCase();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return Objects.equals(tagId, ((Tag)o).tagId);
	}

	@Override
	public int hashCode() {
		return tagId != null ? tagId.hashCode() : 0;
	}

	public static final class Builder {
		private Long tagId;
		private String name;
		private String description;
		private long taggedCount;
		private List<QuestionTag> questions;

		private Builder() {
		}

		public static Builder aTag() {
			return new Builder();
		}

		public Builder tagId(Long tagId) {
			this.tagId = tagId;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder taggedCount(long taggedCount) {
			this.taggedCount = taggedCount;
			return this;
		}

		public Builder questions(List<QuestionTag> questions) {
			this.questions = questions;
			return this;
		}

		public Tag build() {
			Tag tag = new Tag(name);
			tag.taggedCount = this.taggedCount;
			tag.questions = this.questions;
			tag.tagId = this.tagId;
			tag.description = this.description;
			return tag;
		}
	}
}
