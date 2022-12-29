package com.codestates.sof.domain.common;

public enum VoteType {
	NONE(0), UP(1), DOWN(-1);

	private final int value;

	VoteType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
