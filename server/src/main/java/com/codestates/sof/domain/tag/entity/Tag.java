package com.codestates.sof.domain.tag.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;

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

	@ColumnDefault("0")
	@Column(name = "query_count", nullable = false)
	private Long queryCount;

	@OneToMany(mappedBy = "tag")
	private List<QuestionTag> questions = new ArrayList<>();
}
