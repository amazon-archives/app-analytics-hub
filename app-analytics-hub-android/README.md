## App Analytics Hub

This library allows you to instrument your code with operational and behavioral metrics/analytics in your 
Android application.

### Usage:

Add Maven Central repository in project's `build.gradle` file:

```gradle
repositories {
    mavenCentral()
}
```

Add the latest version of AppAnalyticsHub Android library in your application's `build.gradle` file:

```gradle
dependencies {
    ... 
    implementation 'com.amazon.appanalyticshub:appanalyticshub:0.0.1'
    ...    
}

```

Create AppAnalyticsHub instance. 

```java
AppAnalyticsHub appAnalyticsHub = new AppAnalyticsHub();
```

In App Analytics Hub, to record your events in different Analytic systems you would have to register 
different implementations of `AnalyticsCollector` to a specific `EventType` in AppAnalyticsHub. The 
`AnalyticsCollector` implementations will be responsible for understanding the App Analytics Hub 
`Event` and create the respective Analytics system events. For example, if you want all your user 
engagement events be registered in `AWS Pinpoint` you would register the `AWSPinpointAnalyticsCollector` 
to the engagement event type and whenever an event with type user engagement is record in AppAnalyticsHub 
the `AWSPinpointAnalyticsCollector` would be triggered to record the event in `AWSPinpoint`. 

Set a default analytics collector so that every event you push will be recorded in this collector.
```java
// this is optional.
appAnalyticsHub.setDefaultAnalyticsCollector(defaultAnalyticsCollector);
```

Register a collector to App Analytics Hub
```java
appAnalyticsHub.registerCollector(collector);
```

Add and remove an analytics collector to an event type. Adding a collector would also register the 
collector if it is not registered. 

```java
// adding 
appAnalyticsHub.addCollectorToEventType(EventType.OPERATIONAL, operationalAnalyticsCollector);
// removing
appAnalyticsHub.removeCollectorFromEventType(EventType.OPERATIONAL, operationalAnalyticsCollector);
```

Recording an event:

```java
appAnalyticsHub.recordEvent(sampleEvent);
```

Create an event as follows:

1.  Event Factory:

    ```java
    // Create an event Factory
    EventFactory eventFactory = new EventFactory("Authentication" // source is optional 
                                                , EventType.ENGAGEMENT); // event type is optional

    // Using the event factory created, create multiple events with the same source and event type.
    Event sampleEvent = eventFactory.createEvent(
        "AuthSuccess", 
        "Authentication",// source is optional and if not provided will reuse the value from EventFactory
        Priority.HIGH, // priority is optional, will default to `NORMAL` if nothing is provided.
        EventType.OPERATIONAL // eventType is optional if it is provided in the event factory"
    );

    Event sampleEvent1 = eventFactory.createEvent(
        "AuthSuccess",
        EventType.OPERATIONAL // eventType is optional if it is provided in the event factory"
    );
    ```

    While creating an event using event factory `name` is a mandatory field, `source` & `priority` are optional and `eventType` is optional if it provided while creating the event factory.

    If eventType is not provided then during development it would crash your application and in production the event created would have an event type of unknown. This event would only be recorded in the default collector if provided or else it would be lost.

1.  Event constructor:

    ```java
    Event event = new Event(
      "AuthFailure",
      "Authentication", // source is optional 
      EventType.OPERATIONAL,
      Priority.HIGH // priority is optional
    );
    ```

An Event should consists of name and an event type.

An Event-type can be a string or an `EventType` enum. The default event-types provided by App Analytics hub are:

* ENGAGEMENT : All the user engagement event can be linked with ENGAGEMENT event-type.
* OPERATIONAL : All the events related to your operational metrics can be linked with OPERATIONAL event-type.

Events can consists of multiple counter, timers, metrics and data.

Data associated to an event is stored as a map of string to string key value pairs and recording data can be done by simply calling the `addData` method of an event.

```java
sampleEvent.addData("dataKey", "value");
sampleEvent.addData("dataKey1", "value1");
```

Counters associated to an event are stored as a map of string to number key value pairs. Counter can be added, incremented and also removed from an event.

```java
// adding a counter
sampleEvent.addCounter("counter"); // the default value of 1 is used if the value is not provided.
//or
sampleEvent.addCounter("counter", 2);

// incrementing a counter
sampleEvent.incrementCounter("counter"); // the default value of 1 is used if the value is not provided.
//or
sampleEvent.incrementCounter("counter", 3);

// removing a counter
sampleEvent.removeCounter("counter");
```

Metrics associated to an event are stored as a map of string to string or boolean key value pairs. 
Metrics can be added and removed for any type of value but can only be incremented if the value
is a number.

```java
// adding a metric
sampleEvent.addMetric("string metric", "value");
sampleEvent.addMetric("boolean metric", true);

// removing a counter
sampleEvent.removeMetric("metric");
```

Timers associated to an event are stored as a map of string to number key value pairs. Timers can
be added, incremented and also removed from an event.

```java
// adding a timer
sampleEvent.addTimer("timer", 100.0);

// incrementing a timer. Timers don"t have a default value.
sampleEvent.incrementTimer("timer", 200.0);

// removing a timer
sampleEvent.removeTimer("timer");
```

`TimersMetric` are objects that you can use to conveniently measure duration in your application. Like 
a stop watch, they support two operations: `start` and `stop`. Once you have measured a duration in 
your application using a `TimerMetric` object, you can use the `recordTimer` and `recordTimerInEvents` 
operations to record it to one or many events, depending on your use-case.

```java
TimerMetric timer = new TimerMetric("timermetric", parentEvent); // providing a parent event is optional but is advised.
// start your timer
timer.startTimer();
// stop your timer
timer.stopTimer();
// the above methods can be called multiple times and the everytime you stop a timer, time is aggregated to total
// time.

// record the timer to the parentEvent
timer.recordTimer();

// record the timer to other events
timer.recordTimerInEvents(event1, event2, event3); //pass in all the events you want this timer to get recorded.
```
