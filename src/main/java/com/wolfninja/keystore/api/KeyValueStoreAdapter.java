package com.wolfninja.keystore.api;

/**
 * @since 1.0
 */
public interface KeyValueStoreAdapter {

	/**
	 * Get a keyspace instance for the given keyspace name
	 * 
	 * @param keyspaceName
	 * @return {@link Keyspace} instance for the given namespace
	 * @since 1.0
	 */
	Keyspace getKeyspace(String keyspaceName);

}
