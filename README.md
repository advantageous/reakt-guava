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

register(session.executeAsync("SELECT release_version FROM system.local"), 
  promise().thenExpect(expected -> 
     gui.setMessage("Cassandra version is " +
         expected.get().one().getString("release_version"))
  ).catchError(error -> 
     gui.setMessage("Error while reading Cassandra version: " 
     + error.getMessage())
  )
);
     
```

You can also use replay promises, all promises, any promises, and other
features of Reakt to simplify async, reactive Java development.

Reakt gets used by [QBit](http://advantageous.github.io/qbit/), and Conekt.

#### Related projects
* [QBit Reactive Microservices](http://advantageous.github.io/qbit/)
* [Reakt Reactive Java](http://advantageous.github.io/reakt)
* [Reakt Guava Bridge](http://advantageous.github.io/reakt-guava/)
* [QBit Extensions](https://github.com/advantageous/qbit-extensions)
* [Czar Maker Consul](http://advantageous.github.io/czar-maker-consul/)
* [Czar Maker](http://advantageous.github.io/czar-maker/)
* [Reactive Microservices](http://www.mammatustech.com/reactive-microservices)
