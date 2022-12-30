package com.codestates.sof.domain.question.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codestates.sof.domain.member.entity.Member;
import com.codestates.sof.domain.question.entity.Bookmark;
import com.codestates.sof.domain.question.entity.Question;
import com.codestates.sof.domain.question.repository.BookmarkRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {
	private final BookmarkRepository bookmarkRepository;
	private final QuestionService questionService;

	@Transactional
	public boolean bookmark(Member member, long questionId, boolean isUndo) {
		Question question = questionService.findByIdWithoutIncreasingViewCount(questionId);
		Optional<Bookmark> optBookmark = bookmarkRepository.findByMemberAndQuestion(member, question);

		if (optBookmark.isPresent() ^ isUndo) {
			return optBookmark.isPresent();
		} else if (optBookmark.isPresent()) {
			bookmarkRepository.delete(optBookmark.get());
			return false;
		} else {
			bookmarkRepository.save(new Bookmark(member, question));
			return true;
		}
	}

	@Transactional(readOnly = true)
	public Page<Question> getAll(Member member, int page, int size) {
		return bookmarkRepository.findAllByMemberOrderByCreatedAtDesc(member, PageRequest.of(page, size))
			.map(Bookmark::getQuestion);
	}
}
