package com.wolfninja.keystore.api;

import java.io.Serializable;

/**
 * @since 1.0
 */
public class KeyValue implements Serializable {
	private static final long serialVersionUID = 1L;

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
	 */
	public KeyValue(final String key, final String value, final long version) {
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
