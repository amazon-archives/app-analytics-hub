## ART Native Metrics

This library allows you to instrument your code with operational and behavioral metrics in your 
React Native application.

> FYI: This is an [ART Native](https://w.amazon.com/bin/view/AmazonReactToolkitNative/) project, 
seeking to accelerate and improve React Native development at Amazon! 
[Read more](https://w.amazon.com/bin/view/AmazonReactToolkitNative/)

### Usage:

Add the latest version of ARTNativeMetricsAndroid library in your config file:
```
dependencies = {
        1.0 = {
            ARTNativeMetricsAndroid = 1.0;
        };
    };
```

Create ARTNativeMetrics instance. 

```java
ARTNativeMetrics artNativeMetrics = new ARTNativeMetrics();
```

Add ARTNative metric react package to your list of react native packages according to your react 
native integration method.
```java
new ARTNativeMetricsPackage(artNativeMetrics);
```

Set a default metrics collector so that every event you push will be recorded in this collector.
```java
// this is optional.
artNativeMetrics.setDefaultMetricsCollector(defaultMetricCollector);
```
Register a collector to ARTNative metrics
```java
artNativeMetrics.registerCollector(collector);
```

Add and remove a metric collector to an event type. Adding a collector would also register the 
collector if it is not registered. 
```java
// adding 
artNativeMetrics.addCollectorToEventType(eventType,collector);
// removing
artNativeMetrics.removeCollectorFromEventType(eventType,collector);
```

You can record an event using the native module that is imported:

```java
artNativeMetrics.recordEvent(sampleEvent);
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

An Event-type can be a string or an `EventType` enum. The default event-types provided by ART-Native metrics module is:

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


## Contact us

If you"ve found an issue, have a feature request, or would like to ask if this library is right for 
your application, please consider the following channels:

* [Community mailing list: art-native-interest@amazon.com](https://email-list.corp.amazon.com/email-list/expand-list/art-native-interest)
* [ART-Native-Interest Chime Room](https://app.chime.aws/rooms/de3c8c3d-dc30-4ae1-ace0-3a6d8d17759c)
* [ART Native sage tag](https://sage.amazon.com/tags/art-native)
* [ART Issues & Feature Requests](https://sim.amazon.com/issues/create?template=328c1088-1a9e-472b-8500-82314cfd038a)
* [ART Native Office Hours](https://sim.amazon.com/issues/create?template=4665fb14-2484-4bfb-b9ea-78adb82e2b95)

## Interested in contributing?

ART Native has a dedicated team behind it, but we operate using an "internal open source model." We 
gladly accept contributions to ART Native in the form of improvements to documentation, bug fixes, 
or enhancements. Please read [docs/CONTRIBUTING.md](./docs/CONTRIBUTING.md) for a quick description 
of how to submit your pull requests.

## Development

If you would like to setup this package for development, please head over to 
[docs/SETUP.md](./docs/SETUP.md).
