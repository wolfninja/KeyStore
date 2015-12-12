package com.wolfninja.keystore.api;

import org.testng.Assert;
import org.testng.annotations.Test;

@SuppressWarnings("javadoc")
@Test
public abstract class BaseKeyValueStoreAdapterTest {

	private KeyValueStoreAdapter adapter;

	public BaseKeyValueStoreAdapterTest(final KeyValueStoreAdapter adapter) {
		this.adapter = adapter;
	}

	@Test
	public void getKeyspaceShouldReturnKeyspace() {
		final Keyspace actual = adapter.getKeyspace("myKeyspace");
		Assert.assertNotNull(actual);
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void getKeyspaceNonNullable() {
		adapter.getKeyspace(null);
		Assert.fail("Expected exception!");
	}
}
