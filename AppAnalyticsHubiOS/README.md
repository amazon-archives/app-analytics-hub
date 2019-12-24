## App Analytics Hub

This library allows you to instrument your code with operational and behavioral metrics/analytics in your 
iOS application.

### USAGE:

#### Implementation

1. Import the library:
    > Objective-C
    Add the header in your AppDelegate class
    ```objc
        #import "AppAnalyticsHubiOS.h"
    ```
    > Swift
    Add this header to the Bridging header
    ```swift
       #import "AppAnalyticsHubiOS.h"
    ````

2. AppAnalyticsHub is a singleton class which you can access as `AppAnalyticsHub.shared`.
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

3. Set a default metrics collector(optional):
    > Objective-C
      ```objc
        [appAnalyticsHub setDefaultCollector:defaultCollector]
    ```
    > Swift
      ```swift
        appAnalyticsHub.setDefaultCollector(defaultCollector: defaultCollector)
    ```

4. Register a collector:
    > Objective-C
      ```objc
        [appAnalyticsHub registerCollector: testCollector];
    ```
    > Swift
    ```swift
        appAnalyticsHub.registerCollector(testCollector)
    ```

5. Add a collector to an EventType:
    > Objective-C
    ```objc
        [appAnalyticsHub addCollector:metricsCollector toEventType:AppAnalyticsHubEventTypeEngagement]
    ```
    > Swift
    ```swift
        appAnalyticsHub.add(testCollector, toEventType: EventType.engagement)
    ```
6. Remove a collector from an EventType:
    > Objective-C
    ```objc
        [appAnalyticsHub removeCollector:metricsCollector fromEventType:AppAnalyticsHubEventTypeEngagement]
    ```
    > Swift
    ```swift
        appAnalyticsHub.remove(testCollector, toEventType: EventType.engagement)
    ```

7. Record an event:
    > Objective-C
    ```objc
        [appAnalyticsHub recordEvent:metricsEvent]
    ```
    > Swift
    ```swift
        appAnalyticsHub.record(Event(name: "hi", source: "hi", type: EventType.engagement))
    ```
8. Create a custom collector (if required):
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
