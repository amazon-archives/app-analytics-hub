# App Analytics Hub React Native

## Getting started

`$ npm install app-analytics-hub --save`

### Link for React native version < 0.60

`$ react-native link app-analytics-hub`

### Manual installation

#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `app-analytics-hub` and add `AppAnalyticsHubReactNative.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libAppAnalyticsHubReactNative.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`

- Add `import com.reactlibrary.AppAnalyticsHubReactNativePackage;` to the imports at the top of the file
- Add `new AppAnalyticsHubReactNativePackage()` to the list returned by the `getPackages()` method

2. Append the following lines to `android/settings.gradle`:
   ```
   include ':app-analytics-hub-react-native'
   project(':app-analytics-hub-react-native').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-app-analytics-hub-react-native/android')
   ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:

   ```
     compile project(':app-analytics-hub-react-native')
   ```

## Usage

Import the App Analytics Hub module.

```typescript
import { appAnalyticsHub } from 'app-analytics-hub';
```

You can record an event using the native module that is imported:

```typescript
appAnalyticsHub.record(sampleEvent);
```

Create an event as follows:

1.  Event Factory:

    ```typescript
    // Create an event Factory
    import { EventFactory } from 'app-analytics-hub';

    const eventFactory = new EventFactory({
      source: 'source is optional',
      eventType: 'eventType is optional'
    });

    // Using the event factory created, create multiple events with the same source and event type.
    const sampleEvent = eventFactory.createEvent({
      name: 'sampleEvent',
      source: 'source is optional',
      eventType: 'eventType is optional if provided in the event factory',
      priority: 'priority is optional'
    });

    const sampleEvent1 = eventFactory.createEvent({
      name: 'sampleEvent1'
    });
    ```

    While creating an event using event factory `name` is a mandatory field, `source` & `priority` are optional and `eventType` is optional if it provided while creating the event factory.

    If eventType is not provided then during development it would crash your application and in production the event created would have an event type of unknown. This event would only be recorded in the default collector if provided or else it would be lost.

2.  Event constructor:

    ```typescript
    const event = new Event(
      'eventName',
      'eventType',
      'source is optional',
      'priority is optional'
    );
    ```

#### Event Type

An Event should consist of name and an event type. Event types represent categories of events, and you can use event types to differentiate events that have different reporting and transmission requirements.

An Event type can be a string or an `EventType` enum. The default event-types provided by the App Analytics Hub module are:

- `ENGAGEMENT` : All the user engagement events can be linked with ENGAGEMENT event-type.
- `OPERATIONAL` : All the events related to your operational metrics can be linked with OPERATIONAL event-type.

#### Metrics

Events can consists of multiple counter, timers, metrics and data.

Data associated to an event is stored as a map of string to string key value pairs and recording data can be done by simply calling the `addData` method of an event.

```typescript
sampleEvent.addData('dataKey', 'value');
sampleEvent.addData('dataKey1', 'value1');
```

Counters associated to an event are stored as a map of string to number key value pairs. Counter can be added, incremented and also removed from an event.

```typescript
// Adding a counter
sampleEvent.addCounter('counter'); // the default value of 1 is used if the value is not provided.
// Or
sampleEvent.addCounter('counter', 2);

// Incrementing a counter
sampleEvent.incrementCounter('counter'); // the default value of 1 is used if the value is not provided.
// Or
sampleEvent.incrementCounter('counter', 3);

// Removing a counter
sampleEvent.removeCounter('counter');
```

Metrics associated to an event are stored as a map of string to number|string|boolean key value pairs. Metrics can be added and removed for any type of value but can only be incremented if the value is a number.

```typescript
// Adding a metric
sampleEvent.addMetric('stringMetric', 'value');
sampleEvent.addMetric('booleanMetric', true);

// Removing a metric
sampleEvent.removeMetric('metric');
```

##### Timers

Timers associated to an event are stored as a map of string to number key value pairs. Timers can be added, incremented and also removed from an event.

```typescript
// Add a timer
sampleEvent.addTimer('timer', 100.0);

// Increment a timer. Timers don't have a default value.
sampleEvent.incrementTimer('timer', 200.0);

// Removing a timer
sampleEvent.removeTimer('timer');
```

Recording your own durations can often be cumbersome, so we provide a `TimerMetrics` class that you can use to easily measure durations using the provided `start()` and `stop()` methods.

```typescript
import { TimerMetric } from 'app-analytics-hub';

// providing a parent event is optional but is advised.
const timer = new TimerMetric('timermetric', parentEvent);

// Start your timer
timer.startTimer();

// .. time passes

// Stop your timer
timer.stopTimer();

// The above methods can be called multiple times and the everytime you stop a timer, time is aggregated to total
// time.

// Record the timer to the parentEvent (if provided)
timer.recordTimer();

// Record the timer into multiple events
timer.recordTimerInEvents(event1, event2, event3);
```
