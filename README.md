## Reakt to Guava Bridge

[Reakt-Guava Website](http://advantageous.github.io/reakt-guava/)

Guava gets used by many libraries for its async support.
Many NoSQL drivers use Guava, e.g., Cassandra. 

***Guava*** is JDK 1.6 backwards compatible. 

[Reakt](http://advantageous.github.io/reakt/) provides composable 
promises that support lambda expressions, and a fluent API.

This bridge allows you to use ***Reakt's*** promises, reactive streams
and callbacks to have a more modern Java experience with libs like
[Cassandra](http://www.datastax.com/dev/blog/java-driver-async-queries) 
and other libs that use [Guava](https://github.com/google/guava).



#### Cassandra Reakt example

```java

    futureToPromise(session.executeAsync(insertInto("Todo")
            .value("id", todo.getId())
            .value("createTime", todo.getCreateTime())
            .value("name", todo.getName())
            .value("description", todo.getDescription()))
    ).catchError(error -> {
        serviceMgmt.increment("add.todo.fail");
        serviceMgmt.increment("add.todo.fail." +
                error.getClass().getName().toLowerCase());
        recordCassandraError();
        promise.reject("unable to add todo", error);
    }).then(resultSet -> {
        if (resultSet.wasApplied()) {
            promise.resolve(true);
            serviceMgmt.increment("add.todo.success");
        } else {
            promise.resolve(false);
            serviceMgmt.increment("add.todo.fail.not.added");
        }
    }).invokeWithReactor(reactor, Duration.ofSeconds(10)))
     
```

## Getting Started
#### maven
```xml
<dependency>
    <groupId>io.advantageous.reakt</groupId>
    <artifactId>reakt-guava</artifactId>
    <version>3.0.0</version>
</dependency>
```

#### gradle
```java
compile 'io.advantageous.reakt:reakt-guava:3.0.0'
```

You can also use replay promises, all promises, any promises, and other
features of Reakt to simplify async, reactive Java development.

Reakt gets used by [QBit](http://advantageous.github.io/qbit/), and Conekt.

#### Related projects
* [QBit Reactive Microservices](http://advantageous.github.io/qbit/)
* [Reakt Reactive Java](http://advantageous.github.io/reakt)
* [Reakt Guava Bridge](http://advantageous.github.io/reakt-guava/)
* [QBit Extensions](https://github.com/advantageous/qbit-extensions)
* [Elekt Consul](http://advantageous.github.io/elekt-consul/)
* [Elekt](http://advantageous.github.io/elekt/)
* [Reactive Microservices](http://www.mammatustech.com/reactive-microservices)
