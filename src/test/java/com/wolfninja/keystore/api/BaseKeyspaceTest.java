package com.wolfninja.keystore.api;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.google.common.base.Optional;

@Test
public abstract class BaseKeyspaceTest {

	private Keyspace keyspace;

	public BaseKeyspaceTest(final Keyspace keyspace) {
		this.keyspace = keyspace;
	}

	@Test
	public void addingAgainReturnsFalse() {
		final String key = genRandKey();
		final boolean actual = keyspace.add(key, "some value");
		Assert.assertTrue(actual, "Should be able to add a new key");
		final boolean actual2 = keyspace.add(key, "other value");
		Assert.assertFalse(actual2, "Should not be able to add with existing key");
	}

	@Test
	public void checkAndSetShouldReplaceWithSameVersion() {
		final String key = genRandKey();

		// Set initial value
		final boolean resultSetting = keyspace.set(key, "mine!");
		Assert.assertTrue(resultSetting);

		// Get existing value
		final Optional<KeyValue> insertedValue = keyspace.get(key);
		Assert.assertTrue(insertedValue.isPresent());

		// Check and set should return true
		final boolean actual = keyspace.checkAndSet(key, "abcd", insertedValue.get().getVersion());
		Assert.assertTrue(actual);
		final Optional<KeyValue> actualVal = keyspace.get(key);
		// Actual value should be set
		Assert.assertTrue(actualVal.isPresent());
		Assert.assertEquals("abcd", actualVal.get().getValue());
	}

	@Test
	public void checkAndSetShouldReturnFalseOnMismatch() {
		final String key = genRandKey();

		// Set initial value
		final boolean resultSetting = keyspace.set(key, "mine!");
		Assert.assertTrue(resultSetting);

		// Get existing value
		final Optional<KeyValue> insertedValue = keyspace.get(key);
		Assert.assertTrue(insertedValue.isPresent());

		// Check and set should return false
		final boolean actual = keyspace.checkAndSet(key, "abcd", insertedValue.get().getVersion() - 1L);
		Assert.assertFalse(actual);
		final Optional<KeyValue> actualVal = keyspace.get(key);
		// Actual value should be unchanged
		Assert.assertTrue(actualVal.isPresent());
		Assert.assertEquals("mine!", actualVal.get().getValue());
	}

	@Test
	public void checkAndSetShouldReturnFalseOnNewKey() {
		final String key = genRandKey();

		Assert.assertFalse(keyspace.exists(key));
		// Check and set with new key, should do nothing
		final boolean resultSetting = keyspace.checkAndSet(key, "mine!", 43L);
		Assert.assertFalse(resultSetting);
		Assert.assertFalse(keyspace.exists(key));
	}

	@Test
	public void deletingNewKeyShouldReturnFalse() {
		final String key = genRandKey();
		Assert.assertFalse(keyspace.delete(key), "expecting false");
	}

	@Test
	public void existsShouldReturnFalseIfNotExists() {
		final String key = genRandKey();
		Assert.assertFalse(keyspace.exists(key));
	}

	@Test
	public void existsShouldReturnTrueIfExists() {
		final String key = genRandKey();
		Assert.assertFalse(keyspace.exists(key));
		Assert.assertTrue(keyspace.add(key, "something"));
		Assert.assertTrue(keyspace.exists(key));
	}

	private String genRandKey() {
		return UUID.randomUUID().toString();
	}

	@Test
	public void getShouldReturnAbsentIfDeleted() {
		final String key = genRandKey();
		keyspace.add(key, "something awesome");
		Assert.assertTrue(keyspace.exists(key));
		keyspace.delete(key);
		Assert.assertFalse(keyspace.exists(key));
		Assert.assertFalse(keyspace.get(key).isPresent());

	}

	@Test
	public void getShouldReturnAbsentIfNotPresent() {
		final Optional<KeyValue> actual = keyspace.get(genRandKey());
		Assert.assertFalse(actual.isPresent());
	}

	@Test
	public void getShouldReturnDifferentVersionsIfChanged() {
		final String key = genRandKey();
		keyspace.add(key, "something awesome");

		final Optional<KeyValue> first = keyspace.get(key);
		final long firstVersion = first.get().getVersion();

		// Store something else
		keyspace.replace(key, "something else");
		final Optional<KeyValue> second = keyspace.get(key);
		final long secondVersion = second.get().getVersion();
		Assert.assertNotEquals(firstVersion, secondVersion);
	}

	@Test
	public void getShouldReturnIfPresent() {
		final String key = genRandKey();
		keyspace.add(key, "my awesome value");

		final Optional<KeyValue> actual = keyspace.get(key);
		Assert.assertTrue(actual.isPresent());
		KeyValue actualValue = actual.get();
		Assert.assertEquals(actualValue.getKey(), key);
		Assert.assertEquals(actualValue.getValue(), "my awesome value");
	}

	@Test
	public void replacingExistingKeyWithSameValueShouldReturnFalse() {
		final String key = genRandKey();
		final String value = "woot";
		keyspace.add(key, value);
		Assert.assertFalse(keyspace.replace(key, value));
		Assert.assertEquals(keyspace.get(key).get().getValue(), value);
	}

	@Test
	public void setShouldAllowExistingKeys() {
		final String key = genRandKey();

		// Set value for key
		{
			final String value = "woot";
			final boolean actual = keyspace.set(key, value);
			Assert.assertTrue(actual);
			Assert.assertEquals(keyspace.get(key).get().getValue(), value);
		}
		// Add to key again
		{
			final String expected = "newer";
			final boolean actual = keyspace.set(key, expected);
			Assert.assertTrue(actual);
			Assert.assertEquals(keyspace.get(key).get().getValue(), expected);
		}
	}

	@Test
	public void setShouldAllowNewKeys() {
		final String key = genRandKey();
		final String value = "woot";
		final boolean actual = keyspace.set(key, value);
		Assert.assertTrue(actual);
		Assert.assertEquals(keyspace.get(key).get().getValue(), value);
	}

	@Test
	public void shouldBeAbleToAdd() {
		final String key = genRandKey();
		final String value = "some value";
		final boolean actual = keyspace.add(key, value);
		Assert.assertTrue(actual);
		Assert.assertEquals(keyspace.get(key).get().getValue(), value);
	}

	@Test
	public void shouldBeAbleToDelete() {
		final String key = genRandKey();
		final boolean actualAdded = keyspace.add(key, "something");
		Assert.assertTrue(actualAdded);
		Assert.assertTrue(keyspace.exists(key));
		Assert.assertTrue(keyspace.delete(key));
		Assert.assertFalse(keyspace.delete(key));
		Assert.assertFalse(keyspace.exists(key));
	}

	@Test
	public void shouldBeAbleToReplaceExistingKey() {
		final String key = genRandKey();
		keyspace.add(key, "blah");
		final String value = "something else";
		Assert.assertTrue(keyspace.replace(key, value));
		Assert.assertEquals(keyspace.get(key).get().getValue(), value);
	}

	@Test(dataProvider = "shouldNotBeAbleToAddNullsData", expectedExceptions = NullPointerException.class)
	public void shouldNotBeAbleToAddNulls(final String key, final String value) {
		final boolean actual = keyspace.add(key, value);
		Assert.fail("Expected NPE, but returned: " + actual);
	}

	@DataProvider
	public Object[][] shouldNotBeAbleToAddNullsData() {
		return new Object[][] { //
		{ genRandKey(), null }, //
				{ null, "something else" }, //
				{ null, null } //
		};
	}

	@Test(dataProvider = "shouldNotBeAbleToCheckAndSetWithNullsData", expectedExceptions = NullPointerException.class)
	public void shouldNotBeAbleToCheckAndSetWithNulls(final String key, final String value) {
		keyspace.checkAndSet(key, value, 43L);
		Assert.fail("Expected exception!");
	}

	@DataProvider
	protected Object[][] shouldNotBeAbleToCheckAndSetWithNullsData() {
		return new Object[][] { //
		{ null, "something" }, //
				{ genRandKey(), null }, //
				{ null, null }, //
		};
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldNotBeAbleToCheckNullKeyExists() {
		Assert.assertFalse(keyspace.exists(null));
	}

	@Test(expectedExceptions = NullPointerException.class)
	public void shouldNotBeAbleToGetNullKey() {
		keyspace.get(null);
		Assert.fail("Should have thrown exception!");
	}

	@Test
	public void shouldNotBeAbleToReplaceNewKey() {
		Assert.assertFalse(keyspace.replace(genRandKey(), "a value"));
	}

	@Test(dataProvider = "shouldNotBeAbleToReplaceWithNullsData", expectedExceptions = NullPointerException.class)
	public void shouldNotBeAbleToReplaceWithNulls(final String key, final String value) {
		keyspace.replace(key, value);
		Assert.fail("Expected exception!");
	}

	@DataProvider
	protected Object[][] shouldNotBeAbleToReplaceWithNullsData() {
		return new Object[][] { //
		{ null, "something" }, //
				{ genRandKey(), null }, //
				{ null, null }, //
		};
	}
	
	@Test(dataProvider = "shouldNotBeAbleToSetWithNullsData", expectedExceptions = NullPointerException.class)
	public void shouldNotBeAbleToSetWithNulls(final String key, final String value) {
		keyspace.set(key, value);
		Assert.fail("Expected exception!");
	}

	@DataProvider
	protected Object[][] shouldNotBeAbleToSetWithNullsData() {
		return new Object[][] { //
		{ null, "something" }, //
				{ genRandKey(), null }, //
				{ null, null }, //
		};
	}
}
