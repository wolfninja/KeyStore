# KeyStore: Java Key-Value Store Abstraction

*Master branch:*
![Travis CI Build](https://img.shields.io/travis/wolfninja/KeyStore.svg)
[![Code Coverage](https://img.shields.io/codecov/c/github/wolfninja/KeyStore.svg)](https://codecov.io/github/wolfninja/KeyStore)

*Develop branch:*
![Travis CI Build](https://img.shields.io/travis/wolfninja/KeyStore/develop.svg)
[![Code Coverage](https://img.shields.io/codecov/c/github/wolfninja/KeyStore/develop.svg)](https://codecov.io/github/wolfninja/KeyStore?branch=develop)

## Highlights
- Straightforward usage, based on the excellent key commands in Redis
- Stable API featureset versioned via semantic versioning
- Tries to be as defensive as possible (nullable return values wrapped in java8 *Optional*, etc)
- Lightweight with minimal dependencies
- Swappable backends supporting a large number of storage implementations
- Decorator support for drop-in features such as value encryption, compression
- Easy to use SPI for creating new implementations
- Strong test coverage support with full API contract verification

## Latest version
The most recent API release is KeyStore 0.1.0, released December 16, 2015.

### Maven Central
Releases are available via Maven Central: [com.wolfninja.keystore:keystore-api:0.1.0](http://search.maven.org/#artifactdetails|com.wolfninja.keystore|keystore-api|0.1|bundle)

* Group ID: com.wolfninja.keystore
* Artifact ID: keystore-api

### Gradle

```
dependencies {
  compile 'com.wolfninja.keystore:keystore-api:0.1.0'
}
```

### Maven
```xml
    <dependency>
      <groupId>com.wolfninja.keystore</groupId>
      <artifactId>keystore-api</artifactId>
      <version>0.1.0</version>
    </dependency>
```

## Implementations
Other implementations in progress
### Backends
- [keystore-memory (In-Memory)](http://github.com/wolfninja/keystore-memory)
- [keystore-jdbc (JDBC/Database)](http://github.com/wolfninja/keystore-jdbc)
- [keystore-memcached (Memcached)](http://github.com/wolfninja/keystore-memcached)
- [keystore-redis (Redis)](http://github.com/wolfninja/keystore-redis)
### Decorators
#### Compression
- [keystore-decorator-snappy (Google Snappy)](http://github.com/wolfninja/keystore-decorator-snappy)

## Usage Example
```java
		// Create adapter instance (here we are creating a new keystore-memory instance)
		final KeyValueStoreAdapter adapter = MemoryAdapter.create();
		// Create new KeyValueStore instance
		final KeyValueStore store = KeyValueStore.create(adapter);
		// Get key space "myKeyspace"
		final Keyspace keyspace = store.getKeyspace("myKeyspace");

		// Add new value to myKey
		{
			final boolean added = keyspace.add("myKey", "Some cool value");
			assert added;
		}

		// Try adding a different value, this returns false because key already exists
		{
			final boolean readded = keyspace.add("myKey", "Something else");
			assert readded == false;
		}

		// Use replace to overwrite the value
		{
			final boolean overwritten = keyspace.replace("myKey", "Hello world!");
			assert overwritten;
		}

		// Get the value for myKey, should have been overwritten
		{
			final Optional<String> value = keyspace.get("myKey");
			assert value.isPresent();
			assert value.get().equals("Hello world!");
		}

		// Get the value for myOtherKey, doesn't exist
		{
			final Optional<String> value = keyspace.get("myOtherKey");
			assert value.isPresent() == false;
		}
```

## Versioning
- This project uses [Semantic Versioning](http://semver.org/) to make release versions predictable
- Versions consist of MAJOR.MINOR.PATCH
  - Different MAJOR versions are not guaranteed to be API compatible
  - Incrementing MINOR versions within the same MAJOR version contain additional functionality, with existing calls being compatible
  - Different PATCH versions withing the same MAJOR.MINOR version are completely API compatible

## Branches
- *master* is the "stable" branch from which releases are built
- *develop* branch is used for active development, and merged into *master* at release time


## Changelog
See CHANGELOG.md for full history of release changes

## License
Licenced under the MIT License (see LICENSE.txt)
