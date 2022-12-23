package com.codestates.sof.domain.answer.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long answerId;

	@Column(columnDefinition = "MEDIUMTEXT", nullable = false)
	private String content;

	@Column(length = 8)
	private int voteCount;

	@Column(nullable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(nullable = false)
	private LocalDateTime lastModifiedAt = LocalDateTime.now();

	public Answer(String body) {
		this.content = body;
	}
}
