package com.wolfninja.keystore.api;

import java.util.Optional;

import javax.annotation.Nonnull;

import aQute.bnd.annotation.ProviderType;

/**
 * @since 1.0
 *
 */
@ProviderType
public interface Keyspace {
	/**
	 * Add value("Store data only if key does NOT exist")
	 * <p>
	 * Store value only if key has no value
	 * 
	 * @param key
	 *            String key, not null
	 * @param value
	 *            String value, not null
	 * @return true if value added, false otherwise
	 * @since 1.0
	 */
	@Nonnull
	public boolean add(@Nonnull final String key, @Nonnull final String value);

	/**
	 * Check and Set value( "Store data only if nobody else has changed it since I last fetched it")
	 * <p>
	 * Store value only if provided version matches the stored version
	 * 
	 * @param key
	 *            String key, not null
	 * @param value
	 *            String value, not null
	 * @param version
	 *            long version of value
	 * @return true if value added, false otherwise
	 * @since 1.0
	 */
	@Nonnull
	public boolean checkAndSet(@Nonnull final String key, @Nonnull final String value, final long version);

	/**
	 * Delete value for a given key
	 * 
	 * @param key
	 *            String key, not null
	 * @return true if key deleted, false otherwise
	 * @since 1.0
	 */
	@Nonnull
	public boolean delete(@Nonnull final String key);

	/**
	 * Check and Delete value for a given key
	 * 
	 * @param key
	 *            String key, not null
	 * @param version
	 *            long version of value
	 * @return true if key deleted, false otherwise
	 * @since 1.0
	 */
	@Nonnull
	public boolean deletes(@Nonnull final String key, final long version);

	/**
	 * Check if key exists
	 * 
	 * @param key
	 *            String key, not null
	 * @return true if key exists, false otherwise
	 * @since 1.0
	 */
	@Nonnull
	public boolean exists(@Nonnull final String key);

	/**
	 * Get value for a key
	 * 
	 * @param key
	 *            String key, not null
	 * @return {@link String} value wrapped in {@link Optional}. {@link Optional#empty()} if no value for key.
	 * @since 1.0
	 */
	@Nonnull
	public Optional<String> get(@Nonnull final String key);

	/**
	 * Get value for a key with version info
	 * 
	 * @param key
	 *            String key, not null
	 * @return {@link KeyValue} value wrapped in {@link Optional}. {@link Optional#empty()} if no value for key.
	 * @since 1.0
	 */
	@Nonnull
	public Optional<KeyValue> gets(@Nonnull final String key);

	/**
	 * Replace value("Store data only if key exists already")
	 * <p>
	 * Store value only key exists
	 * 
	 * @param key
	 *            String key, not null
	 * @param value
	 *            String value, not null
	 * @return true if value replaced, false otherwise
	 * @since 1.0
	 */
	@Nonnull
	public boolean replace(@Nonnull final String key, @Nonnull final String value);

	/**
	 * Set value ("Store the data")
	 * <p>
	 * Store value regardless if key already exists
	 * 
	 * @param key
	 *            String key, not null
	 * @param value
	 *            String value, not null
	 * @return true if value set, false otherwise
	 * @since 1.0
	 */
	@Nonnull
	public boolean set(@Nonnull final String key, @Nonnull final String value);

}
