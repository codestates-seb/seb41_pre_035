package com.codestates.sof.domain.auth.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.codestates.sof.domain.member.entity.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "TOKENS")
public class VerificationToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String token;

	@OneToOne(fetch = FetchType.LAZY)
	private Member member;

	@Column(nullable = false)
	private Date expiryDate;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false, length = 20)
	private tokenType tokenType;

	public boolean isExpired() {
		return new Date().after(expiryDate);
	}

	public void setExpiryDate(int minutes) {
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, minutes);
		this.expiryDate = now.getTime();
	}

	public enum tokenType {
		VERIFICATION(1),
		PASSWORD_RESET(2);

		@Getter
		private final int tokenTypeId;

		tokenType(int tokenTypeId) {
			this.tokenTypeId = tokenTypeId;
		}
	}
}