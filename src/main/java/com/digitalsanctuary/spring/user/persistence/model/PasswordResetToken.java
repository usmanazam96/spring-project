package com.digitalsanctuary.spring.user.persistence.model;

import java.util.Calendar;
import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

/**
 * The PasswordResetToken Entity.
 */
@Data
@Entity
public class PasswordResetToken {

	/** The Constant EXPIRATION. */
	private static final int EXPIRATION = 60 * 24;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** The token. */
	private String token;

	/** The user. */
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	/** The expiry date. */
	private Date expiryDate;

	/**
	 * Instantiates a new password reset token.
	 */
	public PasswordResetToken() {
		super();
	}

	/**
	 * Instantiates a new password reset token.
	 *
	 * @param token the token
	 */
	public PasswordResetToken(final String token) {
		super();
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	/**
	 * Instantiates a new password reset token.
	 *
	 * @param token the token
	 * @param user the user
	 */
	public PasswordResetToken(final String token, final User user) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

	/**
	 * Calculate expiry date.
	 *
	 * @param expiryTimeInMinutes the expiry time in minutes
	 * @return the date
	 */
	private Date calculateExpiryDate(final int expiryTimeInMinutes) {
		final Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(new Date().getTime());
		cal.add(Calendar.MINUTE, expiryTimeInMinutes);
		return new Date(cal.getTime().getTime());
	}

	/**
	 * Update token.
	 *
	 * @param token the token
	 */
	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate(EXPIRATION);
	}

}
