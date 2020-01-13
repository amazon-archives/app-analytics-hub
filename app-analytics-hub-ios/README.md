## App Analytics Hub

This library allows you to instrument your code with operational and behavioral metrics/analytics in your 
iOS application.

### USAGE:

#### Implementation

Import the library:
    > Objective-C
    Add the header in your AppDelegate class
    ```objc
        #import "AppAnalyticsHubiOS.h"
    ```
    > Swift
    Add this header to the Bridging header
    ```swift
       #import <AppAnalyticsHubiOS/AppAnalyticsHubiOS.h>
    ```

AppAnalyticsHub is a singleton class which you can access as `AppAnalyticsHub.shared`.
    > Objective-C
    ```objc
        AppAnalyticsHub * appAnalyticsHub = AppAnalyticsHub.shared;
    ```
    > Swift
    ```swift
        let appAnalyticsHub = AppAnalyticsHub.shared
    ```

In App Analytics Hub, to record your events in different Analytic systems you would have to register 
different implementations of `AnalyticsCollector` to a specific `AppAnalyticsHubEventType` in AppAnalyticsHub. The 
`AnalyticsCollector` implementations will be responsible for understanding the App Analytics Hub 
`Event` and create the respective Analytics system events. For example, if you want all your user 
engagement events be registered in `AWS Pinpoint` you would register the `AWSPinpointAnalyticsCollector` 
to the engagement event type and whenever an event with type user engagement is record in AppAnalyticsHub 
the `AWSPinpointAnalyticsCollector` would be triggered to record the event in `AWSPinpoint`.

Set a default metrics collector(optional):
    > Objective-C
      ```objc
        [appAnalyticsHub setDefaultCollector:defaultCollector]
    ```
    > Swift
      ```swift
        appAnalyticsHub.defaultCollector = DefaultCollector()
    ```

Register a collector:
    > Objective-C
      ```objc
        [appAnalyticsHub registerCollector: testCollector];
    ```
    > Swift
    ```swift
        appAnalyticsHub.register(testCollector)
    ```

Add a collector to an EventType:
    > Objective-C
    ```objc
        [appAnalyticsHub addCollector:metricsCollector toEventType:AppAnalyticsHubEventTypeEngagement]
    ```
    > Swift
    ```swift
        appAnalyticsHub.add(testCollector, toEventType: EventType.engagement)
    ```
Remove a collector from an EventType:
    > Objective-C
    ```objc
        [appAnalyticsHub removeCollector:metricsCollector fromEventType:AppAnalyticsHubEventTypeEngagement]
    ```
    > Swift
    ```swift
        appAnalyticsHub.remove(testCollector, toEventType: EventType.engagement)
    ```
Create a custom collector (if required):
    > Objective-C
        ```objc
            @implementation TestCollector: NSObject, MetricsCollector {
                var name: String = "test collector";
            }
        - (void)record:(Event) event {
                NSStirng * logString = [NSString stringWithFormat:@"Event Name = %@,  Event Source = %@", event.name, event.source];
                print(@"%@", logString);
         }
        @end
    ```
    > Swift
    ```swift
        class TestCollector: NSObject, MetricsCollector {
            var name: String = "test collector"
            func record(_ event: Event) {
                print("Event Name = ", event.name, "Event Source = ", event.source)
            }
        }
    ```
Creation of event:
    > Objective-C
    ```objc
        AppAnalyticsHubEvent * event = [[AppAnalyticsHubEvent alloc]initWithName:@"AuthSuccess"
                                                                          source:@"Authentication" //source is optional
                                                                            type:AppAnalyticsHubEventTypeOperational
                                                                        priority:AppAnalyticsHubEventPriorityHigh // priority is optional, default to Normal
                                                                    ];
    ```
    > Swift

    ```swift
        let event = Event(name: "AuthSuccess",
                          source: "Authentication", //source is optional
                          type: EventType.operational,
                          priority: EventPriority.high) // priority is optional, default to Normal

    ```
   
An Event should consists of name and an event type.

An Event-type can be a string or an `EventType` enum. The default event-types provided by App Analytics hub are:    
    * ENGAGEMENT : All the user engagement event can be linked with ENGAGEMENT event-type.
    * OPERATIONAL : All the events related to your operational metrics can be linked with OPERATIONAL event-type.

Events can consists of multiple counter, timers, metrics and data.

Data associated to an event is stored as a map of string to string key value pairs and recording data can be done by simply calling the `addData` method of an event.
    > Objective-C
    ```objc
        [event addData:@"value" forKey:@"dataKey"];
        [event addData:@"value1" forKey:@"dataKey1"];
    ```
    > Swift

    ```swift
        event.addData("value", forKey: "dataKey")
        event.addData("value1", forKey: "dataKey1")
    ```

Counters associated to an event are stored as a map of string to number key value pairs. Counter can be added, incremented and also removed from an event.
    > Objective-C
    ```objc
            // adding a counter
            [event addCounterForKey:@"counter"]; // the default value of 1 is used if the value is not provided.
            or
            [event addCounterForKey:@"counter" initialCount:@2];
            
        // incrementing a counter
        [event incrementCounterForKey:@"counter"]
        or 
        [event incrementCounterForKey:@"counter" by:@3];
        
        // removing 
        [event removeCounterForKey:@"counter"];
    ```
    
    > Swift

    ```swift
        // adding a counter
        event.addCounter(forKey: "counter") // the default value of 1 is used if the value is not provided.
        or
        event.addCounter(forKey: "counter", initialCount: 2)
        
        // incrementing a counter
        event.incrementCounter(forKey: "counter")
        or 
        event.incrementCounter(forKey: "counter", by: 3)
        
        // removing 
        event.removeCounter(forKey: "counter")
    ```

Metrics associated to an event are stored as a map of string to string or boolean key value pairs. 
Metrics can be added and removed for any type of value but can only be incremented if the value
is a number.

    > Objective-C
    ```objc
        // adding a metric
        [event addMetric:@"value" forKey: @"string metric"];
        [event addMetric:@true forKey: @"boolean metric"];
        
        // removing a counter
        [event removeMetricForKey: @"string metric"];
    ```
    
    > Swift
    ```swift
        // adding a metric
        event.addMetric("value", forKey: "string metric")
        event.addMetric(true, forKey: "boolean metric")
        
        // removing a counter
        event.removeMetric(forKey: "string metric")
    ```

Timers associated to an event are stored as a map of string to number key value pairs. Timers can
be added, incremented and also removed from an event.

    > Objective c
    ```objc
        // adding a timer
        [event addTimerForKey: @"timer" timeElapsed: @100.0];
    
        // incrementing a timer. Timers don"t have a default value.
        [event incrementTimerForKey: @"timer" by: @200.0];
    
        // removing a timer
        [event removeTimerForKey: @"timer"];
    ```
    
    > Swift
    ```swift
        // adding a timer
        event.addTimer(forKey: "timer", timeElapsed: 100.0)
    
        // incrementing a timer. Timers don"t have a default value.
        event.incrementTimer(forKey: "timer", by: 200.0)
    
        // removing a timer
        event.removeTimer(forKey: "timer")
    ```

`TimersMetric` are objects that you can use to conveniently measure duration in your application. Like 
a stop watch, they support two operations: `start` and `stop`. Once you have measured a duration in 
your application using a `TimerMetric` object, you can use the `recordTimer` and `recordTimerInEvents` 
operations to record it to one or many events, depending on your use-case.

    > Objective c
    ```objc
        AnalyticsHubTimerMetric * timerMetric = [[AnalyticsHubTimerMetric alloc]initWithName:@"timermetric" parent:event]; // providing a parent event is optional but is advised.
        // start your timer
        [timerMetric start];
    
        // stop your timer
        [timerMetric stop];
    
        // the above methods can be called multiple times and the everytime you stop a timer, time is aggregated to total time.
    
        // record the timer to the parentEvent
        [timerMetric record];
    
        // record the timer to other events
        //pass in all the events you want this timer to get recorded.
        [timerMetric recordInEvents:event1, event2, event3];
    ```

    > Swift
    ```swift
        let timer = TimerMetric(name: "timermetric", parent: event) // providing a parent event is optional but is advised.
        // start your timer
        timer.start()
        // stop your timer
        timer.stop()
    
        // the above methods can be called multiple times and the everytime you stop a timer, time is aggregated to total time.
    
        // record the timer to the parentEvent
        timer.record()
    
        // record the timer to other events
        //pass in all the events you want this timer to get recorded.
        timer.record(in: [event1,event2,event3])
    ```

Record an event:
    > Objective-C
    ```objc
        [appAnalyticsHub recordEvent:event]
    ```
    > Swift
    ```swift
        appAnalyticsHub.record(event)
    ```
 
