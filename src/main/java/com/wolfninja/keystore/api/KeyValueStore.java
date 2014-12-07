package com.wolfninja.keystore.api;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

/**
 * @since 1.0
 */
public class KeyValueStore {

	/**
	 * Create a new instance of {@code KeyValueStore} for the given {@code KeyValueStoreAdapter}
	 * 
	 * @param adapter
	 *            {@link KeyValueStoreAdapter} adapter instance, not null
	 * @return {@link KeyValueStore} backed by the provided adapter, not null
	 * @since 1.0
	 */
	@Nonnull
	public static KeyValueStore create(@Nonnull final KeyValueStoreAdapter adapter) {
		Preconditions.checkNotNull("Adapter must not be null");
		return new KeyValueStore(adapter);
	}

	private final KeyValueStoreAdapter adapter;

	private KeyValueStore(@Nonnull final KeyValueStoreAdapter adapter) {
		Preconditions.checkNotNull(adapter, "Adapter must not be null");
		this.adapter = adapter;
	}

	/**
	 * Get the given {@code Keyspace} by name
	 * 
	 * @param keyspaceName
	 *            String name of keyspace, not null
	 * @return {@link Keyspace} instance, not null
	 * @since 1.0
	 */
	@Nonnull
	public Keyspace getKeyspace(@Nonnull final String keyspaceName) {
		Preconditions.checkNotNull(keyspaceName, "Keyspace name must not be null");
		return adapter.getKeyspace(keyspaceName);
	}
}
