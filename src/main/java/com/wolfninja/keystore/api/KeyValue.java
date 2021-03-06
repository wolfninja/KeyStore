package com.wolfninja.keystore.api;

import java.io.Serializable;
import java.util.Objects;

import javax.annotation.Nonnull;

import aQute.bnd.annotation.ProviderType;

/**
 * @since 0.1
 */
@ProviderType
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
	 * @since 0.1
	 */
	@Nonnull
	public static KeyValue create(@Nonnull final String key, @Nonnull final String value, @Nonnull final long version) {
		Objects.requireNonNull(key, "Key must not be null");
		Objects.requireNonNull(value, "Value must not be null");
		Objects.requireNonNull(version, "Version must not be null");
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
	 * @since 0.1
	 */
	protected KeyValue(final String key, final String value, final long version) {
		Objects.requireNonNull(key, "Key must not be null");
		Objects.requireNonNull(value, "Value must not be null");
		Objects.requireNonNull(version, "Version must not be null");

		this.key = key;
		this.value = value;
		this.version = version;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof KeyValue))
			return false;
		final KeyValue other = (KeyValue) obj;
		return Objects.equals(key, other.key) && Objects.equals(value, other.value)
				&& Objects.equals(version, other.version);
	}

	/**
	 * Get key
	 * 
	 * @return String key
	 * @since 0.1
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Get value
	 * 
	 * @return String value
	 * @since 0.1
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Get version
	 * 
	 * @return long version
	 * @since 0.1
	 */
	public long getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return Objects.hash(key, value, version);
	}

}
