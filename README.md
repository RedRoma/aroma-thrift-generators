Aroma Thrift Generators
==============================================

[<img src="https://raw.githubusercontent.com/RedRoma/aroma/develop/Graphics/Logo.png" width="300">](https://github.com/RedRoma/aroma)

<!--
[<p align="center"><img src="https://raw.githubusercontent.com/RedRoma/aroma/develop/Graphics/Logo.png" width="300"></p>](https://github.com/RedRoma/aroma)
-->

[![Build Status](http://jenkins.sirwellington.tech/job/Aroma%20Thrift/badge/icon)](http://jenkins.sirwellington.tech/job/Aroma%20Thrift/)

This Project contains `AlchemyGenerator` instances for creation of common AromaThrift Objects

# Download

To use, simply add the following maven dependency.

## Release
```xml
<dependency>
	<groupId>tech.aroma</groupId>
	<artifactId>aroma-thrift-generators</artifactId>
	<version>1.0</version>
</dependency>
```

## Snapshot

>First add the Snapshot Repository
```xml
<repository>
	<id>ossrh</id>
    <url>https://oss.sonatype.org/content/repositories/snapshots</url>
</repository>
```

```xml
<dependency>
	<groupId>tech.aroma</groupId>
	<artifactId>aroma-thrift-generators</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

# [Javadocs](http://www.javadoc.io/doc/tech.aroma/aroma-thrift-generators/)

# Requirements

+ Java 8
+ Maven
+ Thrift 0.9.3 Compiler

# Building
This project builds with maven. Just run a `mvn clean install` to compile and install to your local maven repository. 
