package com.wolfninja.keystore.api;

import javax.annotation.Nonnull;

/**
 * @since 1.0
 */
public interface KeyValueStoreAdapter {

	/**
	 * Get a keyspace instance for the given keyspace name
	 * 
	 * @param keyspaceName
	 *            String keyspace name, not null
	 * @return {@link Keyspace} instance for the given namespace, not null
	 * @since 1.0
	 */
	@Nonnull
	Keyspace getKeyspace(@Nonnull String keyspaceName);

}
