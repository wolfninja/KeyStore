package com.wolfninja.keystore.api;

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
		String key = "second.key";
		final boolean actual = keyspace.add(key, "some value");
		Assert.assertTrue(actual, "Should be able to add a new key");
		final boolean actual2 = keyspace.add(key, "other value");
		Assert.assertFalse(actual2, "Should not be able to add with existing key");
	}

	@Test
	public void checkAndSet() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void deletingNewKeyShouldReturnFalse() {
		final String key = "fourth.key";
		Assert.assertFalse(keyspace.delete(key), "expecting false");
	}

	@Test
	public void existsShouldReturnFalseIfNotExists() {
		final String key = "sixth.key";
		Assert.assertFalse(keyspace.exists(key));
	}

	@Test
	public void existsShouldReturnTrueIfExists() {
		final String key = "fifth.key";
		Assert.assertFalse(keyspace.exists(key));
		Assert.assertTrue(keyspace.add(key, "something"));
		Assert.assertTrue(keyspace.exists(key));
	}

	@Test
	public void getShouldReturnAbsentIfDeleted() {
		String key = "tenth.key";
		keyspace.add(key, "something awesome");
		Assert.assertTrue(keyspace.exists(key));
		keyspace.delete(key);
		Assert.assertFalse(keyspace.exists(key));
		Assert.assertFalse(keyspace.get(key).isPresent());

	}

	@Test
	public void getShouldReturnAbsentIfNotPresent() {
		final Optional<KeyValue> actual = keyspace.get("seventh.key");
		Assert.assertFalse(actual.isPresent());
	}

	@Test
	public void getShouldReturnDifferentVersionsIfChanged() {
		String key = "ninth.key";
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
		String key = "eigth.key";
		keyspace.add(key, "my awesome value");

		final Optional<KeyValue> actual = keyspace.get(key);
		Assert.assertTrue(actual.isPresent());
		KeyValue actualValue = actual.get();
		Assert.assertEquals(actualValue.getKey(), key);
		Assert.assertEquals(actualValue.getValue(), "my awesome value");
	}

	@Test
	public void set() {
		throw new RuntimeException("Test not implemented");
	}

	@Test
	public void shouldBeAbleToAdd() {
		final String key = "first.key";
		final String value = "some value";
		final boolean actual = keyspace.add(key, value);
		Assert.assertTrue(actual);
		Assert.assertEquals(keyspace.get(key).get().getValue(), value);
	}

	@Test
	public void shouldBeAbleToDelete() {
		final String key = "fourteenth.key";
		final boolean actualAdded = keyspace.add(key, "something");
		Assert.assertTrue(actualAdded);
		Assert.assertTrue(keyspace.exists(key));
		Assert.assertTrue(keyspace.delete(key));
		Assert.assertFalse(keyspace.delete(key));
		Assert.assertFalse(keyspace.exists(key));
	}

	@Test
	public void shouldBeAbleToReplaceExistingKey() {
		final String key = "twelfth.key";
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
		{ "third.key", null }, //
				{ null, "something else" }, //
				{ null, null } //
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
		Assert.assertFalse(keyspace.replace("thirteenth.key", "a value"));
	}

	@Test(dataProvider = "shouldNotBeAbleToReplaceWithNullsData")
	public void shouldNotBeAbleToReplaceWithNulls(final String key, final String value) {
		keyspace.replace(key, value);
		Assert.fail("Expected exception!");
	}

	@DataProvider
	protected Object[][] shouldNotBeAbleToReplaceWithNullsData() {
		return new Object[][] { //
		{ null, "something" }, //
				{ "eleventh.key", null }, //
				{ null, null }, //
		};
	}
}
