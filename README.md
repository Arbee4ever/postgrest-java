# PostgREST-java
[![CircleCI](https://circleci.com/gh/Harium/postgrest-java.svg?style=svg)](https://circleci.com/gh/Harium/postgrest-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.harium/postgrest/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.harium/postgrest/)

A Java client for [PostgREST](https://postgrest.org/en/stable/).

### Features
Supports basic operations: INSERT, UPSERT, DELETE.
Most of the Horizontal Filtering features are also implemented.

### Example
```java
PostgrestClient client = new PostgrestClient("localhost:3000").withHttps(false);
client.insert("table", Insert.row().column("email", "email@example.com").column("name", "The User"));
System.out.println(client.findAll("table"));
client.delete("table", Condition.eq("id", 1));
```

#### Not implemented yet
- Full Text search
- Casting
- Ordering
- Limits and Pagination
- Count
- Singular or Plural
- Custom Queries
- Stored Procedures
- Binary Output
- OpenAPI Support