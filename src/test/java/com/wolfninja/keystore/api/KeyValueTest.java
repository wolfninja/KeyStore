package com.wolfninja.keystore.api;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Tests for {@code KeyValue}
 */
@Test
public class KeyValueTest {

	/**
	 * Test static create method
	 */
	@Test
	public void createTest() {
		final String expectedKey = "key";
		final String expectedValue = "value";
		final long expectedVersion = 44L;

		final KeyValue actual = KeyValue.create(expectedKey, expectedValue, expectedVersion);

		Assert.assertEquals(actual.getKey(), expectedKey);
		Assert.assertEquals(actual.getValue(), expectedValue);
		Assert.assertEquals(actual.getVersion(), expectedVersion);
	}

	/**
	 * Test data for {@link #createNotNullableTest(String, String, long)}
	 * 
	 * @return Test data
	 */
	@DataProvider
	protected Object[][] createNotNullableData() {
		return new Object[][] { //
		{ null, "value", 1234L }, //
				{ "key", null, 1234L }, //
				{ null, null, 0L } //
		};

	}

	/**
	 * Test creating an instance with null data, expecting exception
	 * 
	 * @param expectedKey
	 *            String key
	 * @param expectedValue
	 *            String value
	 * @param expectedVersion
	 *            long version
	 */
	@Test(dataProvider = "createNotNullableData", expectedExceptions = NullPointerException.class)
	public void createNotNullableTest(final String expectedKey, final String expectedValue, final long expectedVersion) {
		final KeyValue actual = KeyValue.create(expectedKey, expectedValue, expectedVersion);
		Assert.fail("Expected exception! Value of actual: " + actual);
	}

	/**
	 * Test key getter
	 */
	@Test
	public void getKeyTest() {
		final String expectedKey = "key";
		final String expectedValue = "value";
		final long expectedVersion = 44L;

		final KeyValue actual = KeyValue.create(expectedKey, expectedValue, expectedVersion);
		Assert.assertEquals(actual.getKey(), expectedKey);
	}

	/**
	 * Test value getter
	 */
	@Test
	public void getValueTest() {
		final String expectedKey = "key";
		final String expectedValue = "value";
		final long expectedVersion = 44L;

		final KeyValue actual = KeyValue.create(expectedKey, expectedValue, expectedVersion);
		Assert.assertEquals(actual.getValue(), expectedValue);
	}

	/**
	 * Test version getter
	 */
	@Test
	public void getVersionTest() {
		final String expectedKey = "key";
		final String expectedValue = "value";
		final long expectedVersion = 44L;

		final KeyValue actual = KeyValue.create(expectedKey, expectedValue, expectedVersion);
		Assert.assertEquals(actual.getVersion(), expectedVersion);
	}
}
