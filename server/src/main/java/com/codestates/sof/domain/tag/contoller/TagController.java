package com.codestates.sof.domain.tag.contoller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codestates.sof.domain.tag.dto.TagDto;
import com.codestates.sof.domain.tag.entity.Tag;
import com.codestates.sof.domain.tag.mapper.TagMapper;
import com.codestates.sof.domain.tag.service.TagService;
import com.codestates.sof.domain.tag.support.TagPageRequest;
import com.codestates.sof.global.dto.MultiResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
	private final TagService tagService;
	private final TagMapper mapper;

	@GetMapping
	public ResponseEntity<?> getAll(TagPageRequest pageRequest) {
		return getMultiResponseEntity(tagService.findAll(pageRequest));
	}

	@GetMapping("search")
	public ResponseEntity<?> searchByQuery(@RequestParam(name = "q") String query, TagPageRequest pageRequest) {
		return getMultiResponseEntity(tagService.search(query, pageRequest));
	}

	private ResponseEntity<?> getMultiResponseEntity(Page<Tag> page) {
		if (page.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			List<TagDto.Response> responses = page.map(mapper::tagToResponse).toList();
			return new ResponseEntity<>(new MultiResponseDto<>(responses, page), HttpStatus.OK);
		}
	}
}
