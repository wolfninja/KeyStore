package com.wolfninja.keystore.api;

import org.easymock.EasyMock;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Tests for {@code KeyValueStore}
 */
@Test
public class KeyValueStoreTest {

	/**
	 * Verify that can create a {@link KeyValueStore} backed by the
	 */
	@Test
	public void createTest() {
		final KeyValueStoreAdapter mockAdapter = EasyMock.createStrictMock(KeyValueStoreAdapter.class);
		final KeyValueStore kvs = KeyValueStore.create(mockAdapter);
		Assert.assertNotNull(kvs);
	}

	/**
	 * Verify that passing in a null adapter throws exception
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void createNotNullableTest() {
		KeyValueStore.create(null);
		Assert.fail("Should have thrown exception!");
	}

	/**
	 * Verify that get keyspace delegates to adapter correctly
	 */
	@Test
	public void getKeyspaceTest() {
		final KeyValueStoreAdapter mockAdapter = EasyMock.createStrictMock(KeyValueStoreAdapter.class);
		final Keyspace expected = EasyMock.createStrictMock(Keyspace.class);
		EasyMock.expect(mockAdapter.getKeyspace("a.b.c")).andReturn(expected);

		EasyMock.replay(mockAdapter, expected);

		final KeyValueStore kvs = KeyValueStore.create(mockAdapter);
		Assert.assertNotNull(kvs);

		final Keyspace actual = kvs.getKeyspace("a.b.c");
		Assert.assertEquals(actual, expected);

		EasyMock.verify(mockAdapter, expected);
	}

	/**
	 * Verify that passing in a null keyspace name throws exception
	 */
	@Test(expectedExceptions = NullPointerException.class)
	public void getKeyspaceNotNullableTest() {
		final KeyValueStoreAdapter mockAdapter = EasyMock.createStrictMock(KeyValueStoreAdapter.class);
		final Keyspace expected = EasyMock.createStrictMock(Keyspace.class);

		EasyMock.replay(mockAdapter, expected);

		final KeyValueStore kvs = KeyValueStore.create(mockAdapter);
		Assert.assertNotNull(kvs);

		kvs.getKeyspace(null);

		EasyMock.verify(mockAdapter, expected);
	}
}
