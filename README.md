Aroma Thrift Generators
==============================================

[<img src="https://raw.githubusercontent.com/RedRoma/aroma/develop/Graphics/Logo.png" width="300">](http://aroma.redroma.tech/)


[![Build Status](http://jenkins.redroma.tech/view/Aroma/job/Aroma%20Thrift%20Generators/badge/icon)](http://jenkins.redroma.tech/view/Aroma/job/Aroma%20Thrift%20Generators/)

This Project contains `AlchemyGenerator` instances for creation of common [AromaThrift](https://github.com/RedRoma/aroma-thrift/) Objects

# Download

To use, simply add the following maven dependency.

## Release
```xml
<dependency>
	<groupId>tech.aroma</groupId>
	<artifactId>aroma-thrift-generators</artifactId>
	<version>2.0.1</version>
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
	<version>2.1-SNAPSHOT</version>
</dependency>
```

# [Javadocs](http://www.javadoc.io/doc/tech.aroma/aroma-thrift-generators/)

# Requirements

+ Java 8
+ Maven

# Building
This project builds with maven. Just run a `mvn clean install` to compile and install to your local maven repository.
