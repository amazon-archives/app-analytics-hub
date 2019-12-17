## App Analytics Hub

This library allows you to instrument your code with operational and behavioral metrics/analytics in your 
Android application.

### Usage:

Add the latest version of AppAnalyticsHub Android library in your applications `build.gradle` file:

```gradle
dependencies {
    ... 
    implementation 'com.amazon.appanalyticshub:appanalyticshub:1.0.0'
    ...    
}

```

Create AppAnalyticsHub instance. 

```java
AppAnalyticsHub appAnalyticsHub = new AppAnalyticsHub();
```

Set a default analytics collector so that every event you push will be recorded in this collector.
```java
// this is optional.
appAnalyticsHub.setDefaultAnalyticsCollector(defaultAnalyticsCollector);
```

Register a collector to App Analytics Hub
```java
appAnalyticsHub.registerCollector(collector);
```

Add and remove a analytics collector to an event type. Adding a collector would also register the 
collector if it is not registered. 

```java
// adding 
appAnalyticsHub.addCollectorToEventType(eventType,collector);
// removing
appAnalyticsHub.removeCollectorFromEventType(eventType,collector);
```

Recording an event:

```java
appAnalyticsHub.recordEvent(sampleEvent);
```

Create a event as follows:

1.  Event Factory:

    ```java
    // Create an event Factory
    EventFactory eventFactory = new EventFactory("source is optional","eventType is optional");

    // Using the event factory created, create multiple events with the same source and event type.
    Event sampleEvent = eventFactory.createEvent(
        "sampleEvent", 
        "source is optional",
        "priority is optional",
        "eventType is optional if it provided in the event factory"
    );

    Event sampleEvent1 = eventFactory.createEvent(
        "sampleEvent1",
        null,
        null,
        "eventType is optional if it provided in the event factory");
    ```

    While creating an event using event factory `name` is a mandatory field, `source` & `priority` are optional and `eventType` is optional if it provided while creating the event factory.

    If eventType is not provided then during development it would crash your application and in production the event created would have an event type of unknown. This event would only be recorded in the default collector if provided or else it would be lost.

1.  Event constructor:

    ```java
    Event event = new Event(
      "event name",
      "source is optional",
      "event type",
      "priority is optional"
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

Timers needs functionalities of start and stop to get the value needed to be recorded by an event which are provided by `TimerMetric` object. Using TimerMetric object you can create a timer start and stop it multiple times and the record it associate this metric to a single event or multiple events.

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
