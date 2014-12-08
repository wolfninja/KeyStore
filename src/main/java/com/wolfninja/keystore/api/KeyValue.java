package com.wolfninja.keystore.api;

import java.io.Serializable;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * @since 1.0
 */
public class KeyValue implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Create new {@code KeyValue} instance
	 * 
	 * @param key
	 *            String key, not null
	 * @param value
	 *            String value, not null
	 * @param version
	 *            long version, not null
	 * @return new {@link KeyValue} instance
	 * @since 1.0
	 */
	@Nonnull
	public static KeyValue create(@Nonnull final String key, @Nonnull final String value, @Nonnull final long version) {
		Preconditions.checkNotNull(key, "Key must not be null");
		Preconditions.checkNotNull(value, "Value must not be null");
		Preconditions.checkNotNull(version, "Version must not be null");
		return new KeyValue(key, value, version);
	}

	private final String key;
	private final String value;
	private final long version;

	/**
	 * Create new {@link KeyValue}
	 * 
	 * @param key
	 *            String key
	 * @param value
	 *            String value
	 * @param version
	 *            long version
	 * @since 1.0
	 */
	protected KeyValue(final String key, final String value, final long version) {
		Preconditions.checkNotNull(key, "Key must not be null");
		Preconditions.checkNotNull(value, "Value must not be null");
		Preconditions.checkNotNull(version, "Version must not be null");

		this.key = key;
		this.value = value;
		this.version = version;
	}

	/**
	 * Get key
	 * 
	 * @return String key
	 * @since 1.0
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get value
	 * 
	 * @return String value
	 * @since 1.0
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Get version
	 * 
	 * @return long version
	 * @since 1.0
	 */
	public long getVersion() {
		return version;
	}

}
