# java-algorand-sdk

[![Build Status](https://travis-ci.com/algorand/java-algorand-sdk.svg?branch=master)](https://travis-ci.com/algorand/java-algorand-sdk?branch=master)

AlgoSDK is a Java library for communicating and interacting with the Algorand network. It contains a REST client for accessing `algod` instances over the web,
and also exposes functionality for generating keypairs, mnemonics, creating transactions, signing transactions, and serializing data across the network.


# Prerequisites

Java 7+ and Android `minSdkVersion` 16+

# Installation

Maven:

```xml
<dependency>
    <groupId>com.algorand</groupId>
    <artifactId>algosdk</artifactId>
    <version>1.1.0</version>
</dependency>
```
To fetch it straight from the algorand repository, on github, also add in your `pom.xml`:
```xml
<repositories>
    <repository>
        <id>algosdk-mvn-repo</id>
        <url>https://raw.github.com/algorand/java-algorand-sdk/mvn-repo/</url>
        <snapshots>
            <enabled>true</enabled>
            <updatePolicy>always</updatePolicy>
        </snapshots>
    </repository>
</repositories>
```


# Quickstart

```java
package com.algorand.algosdk.example;

import com.algorand.algosdk.algod.client.AlgodClient;
import com.algorand.algosdk.algod.client.ApiException;
import com.algorand.algosdk.algod.client.api.AlgodApi;
import com.algorand.algosdk.algod.client.auth.ApiKeyAuth;
import com.algorand.algosdk.algod.client.model.NodeStatus;


public class Main {

    public static void main(String args[]) throws Exception {
        final String ALGOD_API_ADDR = "http://localhost:8080";
        final String ALGOD_API_TOKEN = "d6f33a522f465ff12f0d263f2c3b707ac2f560bacad4d859914ada7e827902b3";

        AlgodClient client = new AlgodClient();
        client.setBasePath(ALGOD_API_ADDR);
        ApiKeyAuth api_key = (ApiKeyAuth) client.getAuthentication("api_key");
        api_key.setApiKey(ALGOD_API_TOKEN);

        AlgodApi algodApiInstance = new AlgodApi(client);
        try {
            NodeStatus status = algodApiInstance.getStatus();
            System.out.println("Algorand network status: " + status);
        } catch (ApiException e) {
            System.err.println("Exception when calling algod#getStatus");
            e.printStackTrace();
        }
    }

}
```
This prints:
```
Algorand network status: class NodeStatus {
    catchupTime: 0
    lastConsensusVersion: v4
    lastRound: 260318
    nextConsensusVersion: v4
    nextConsensusVersionRound: 260319
    nextConsensusVersionSupported: true
    timeSinceLastRound: 3620331759
}
```

# Documentation

Javadoc can be found at (https://algorand.github.io/java-algorand-sdk/). Additional resources are located at (https://developer.algorand.org).

# Cryptography

AlgoSDK depends on `org.bouncycastle:bcprov-jdk15on:1.61` for `Ed25519` signatures, `sha512/256` digests, and deserializing `X.509`-encoded `Ed25519` private keys.
The latter is the only explicit dependency on an external crypto library - all other references are abstracted through the JCA.

# Longer Example

Take a look at the example located in the `examples/` directory.

# Java 9+

When using cryptographic functionality, and Java9+, you may run into the following warning:
```
WARNING: Illegal reflective access by org.bouncycastle.jcajce.provider.drbg.DRBG
```
This is known behavior, caused by more restrictive language features in Java 9+, that Bouncy Castle has yet to support. This warning can be suppressed safely. We will monitor
cryptographic packages for updates or alternative implementations.

# Contributing to this Project

## bazel build

This project uses `bazel` and also supports `mvn`.
In the bazel context, it uses the `maven_install` bazel rule to fetch `maven` dependencies.
More information can be found at https://blog.bazel.build/2019/03/31/rules-jvm-external-maven.html.

We should monitor the progress of features such as artifact checksumming.

To run the example project:
```
bazel run //examples/src/main/java/com/algorand/algosdk/example:example
```

To build the whole library:
`bazel build //:algosdk`

To build individual pieces:
`bazel build //:algosdk_core`
`bazel build //:algosdk_rest_api`

To test:
`bazel test //...`

To build the pom file (this auto-populates the dependencies from bazel dependencies):
```
bazel build //:algosdk_pom
```

To build specific libraries (pick and choose, e.g. for account library)
`bazel build //src/main/java/com/algorand/algosdk/account:account`

## maven support

The generated pom file provides maven compatibility and deploy capabilities.
```
mvn clean install
mvn clean deploy -P github
mvn clean site -P github  # for javadoc
mvn clean deploy -P release
```
Please update the appropriate bazel files if you use maven and contribute to the project.

## Updating the `algod` and `kmd` REST clients
The `algod` and `kmd` REST clients are largely autogenerated by `swagger-codegen`. [https://github.com/swagger-api/swagger-codegen]

To regenerate the clients, first, check out the latest `swagger-codegen` from the github repo. (In particular, the Homebrew version
is out of date and fails to handle raw byte arrays properly). Note OpenAPI 2.0 doesn't support unsigned types. Luckily we don't have any
uint32 types in algod, so we can do a lossless type-mapping fromt uint64->int64 (Long) -> BigInteger:

```
curl http://localhost:8080/swagger.json | sed -e 's/uint64/int64/g' > temp.json
swagger-codegen generate -i temp.json -l java -c config.json --type-mappings Long=java.math.BigInteger
```

For kmd:
```
curl http://localhost:8080/swagger.json | sed -e 's/uint32/int64/g' > temp.json
swagger-codegen generate -i temp.json -l java -c config.json
```

`config.json` looks like:
```json
{
  "library": "okhttp-gson",
  "java8": false,
  "hideGenerationTimestamp": true,
  "serializableModel": false,
  "supportJava6": true,
  "invokerPackage": "com.algorand.algosdk.{kmd or algod}.client",
  "apiPackage": "com.algorand.algosdk.{kmd or algod}.client.api",
  "modelPackage": "com.algorand.algosdk.{kmd or algod}.client.model"
}
```

For kmd, make sure you convert all `uint32` types to `Long` types.

Finally, make sure to update all (hardcoded) `BUILD.bazel` files appropriately. In particular, the generated code
(as of April 2019) has one circular dependency involving `client.Pair`. The `client` package depends on `client.auth`, but
`client.auth` uses `client.Pair` which is in the `client` package. One more problem is that `uint64` is not a valid format
in OpenAPI 2.0; however, we need to send large integers to the `algod` API (`kmd` is fine).
To resolve this, we do the following manual pass on generated code:

- Move `Pair.java` into the `client.lib` package (with appropriate `BUILD.bazel` file and renaming);
- Find-and-replace `Integer` with `BigInteger` (for uint64), `Long` (for uint32), etc. in `com.algorand.algosdk.algod` and subpackages;
- Run an `Optimize Imports` operation on generated code, to minimize dependencies.

Note that msgpack-java is good at using the minimal representation.


# Android Support

Significant work has been taken to ensure Android compatibility (in particular for `minSdkVersion` 16). Note that the
default crypto provider on Android does not provide `ed25519` signatures, so you will need to provide your own (e.g. `BouncyCastle`).
