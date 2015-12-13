package com.wolfninja.keystore.api;

import javax.annotation.Nonnull;

import aQute.bnd.annotation.ProviderType;

/**
 * @since 0.1
 */
@ProviderType
public interface KeyValueStoreAdapter {

	/**
	 * Get a keyspace instance for the given keyspace name
	 * 
	 * @param keyspaceName
	 *            String keyspace name, not null
	 * @return {@link Keyspace} instance for the given namespace, not null
	 * @since 0.1
	 */
	@Nonnull
	Keyspace getKeyspace(@Nonnull String keyspaceName);

}
