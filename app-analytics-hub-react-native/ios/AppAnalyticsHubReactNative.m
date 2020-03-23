//
//  Created by Naveen Thontepu  on 12/4/19.
//  Copyright © 2019 Amazon. All rights reserved.
//

#import "AppAnalyticsHubReactNative.h"

@interface AppAnalyticsHubReactNative ()

@property(nonatomic, readonly, nonnull) id<AAHAnalyticsHub> analyticsHub;

@end

@implementation AppAnalyticsHubReactNative

RCT_EXPORT_MODULE(AppAnalyticsHub)

static NSString * const kEventName = @"name";
static NSString * const kEventSource = @"source";
static NSString * const kEventType = @"eventType";
static NSString * const kEventPriority = @"priority";
static NSString * const kEventData = @"data";
static NSString * const kEventCounters = @"counters";
static NSString * const kEventTimers = @"timers";
static NSString * const kEventMetrics = @"metrics";

/**
 Initializes a new ART Native metrics React Native module.
 
 @return Instance of the module
 */
- (instancetype)init {
    self = [super init];
    if (self) {
        _analyticsHub = [AppAnalyticsHub shared];
    }
    return self;
}

+ (BOOL)requiresMainQueueSetup {
    return NO;
}

/**
 Records the provided event.
 
 @param event The event in dictionary format to record.
 */
RCT_EXPORT_METHOD(recordEvent : (NSDictionary<NSString *, id> *)dictionary) {
    AppAnalyticsHubEvent * event = [self eventFromDictionary:dictionary];
    if (event != nil) {
        [self.analyticsHub recordEvent:event];
    }
}

- (id<AAHAnalyticsCollector>)findRegisteredCollectorWithName:(NSString *)name {
    __block id<AAHAnalyticsCollector> collector;
    [self.analyticsHub.registeredCollectors enumerateObjectsUsingBlock:^(
                                                                           id<AAHAnalyticsCollector> _Nonnull obj, NSUInteger idx, BOOL * _Nonnull stop) {
        if ([obj.name isEqualToString:name]) {
            collector = obj;
            *stop = YES;
        }
    }];
    return collector;
}

/**
 Adds the previously registered collector to the specified event type.
 
 @param name The name of the previously registered collector to add.
 @param eventType The event type to add the collector to.
 */
RCT_EXPORT_METHOD(addCollector : (NSString *)name toEventType : (NSString *)eventType) {
    NSAssert(name.length > 0, @"Collector name must not be empty");
    NSAssert(eventType.length > 0, @"Event type must not be empty");
    id<AAHAnalyticsCollector> collector = [self findRegisteredCollectorWithName:name];
    if (collector != nil) {
        [self.analyticsHub addCollector:collector toEventType:eventType];
    } else {
        NSLog(@"Could not add a registered collector with name %@.", name);
    }
}

/**
 Removes the previously registered collector from the specified event type.
 
 @param name The name of the previously registered collector to remove.
 @param eventType The event type to remove the collector from.
 */
RCT_EXPORT_METHOD(removeCollector : (NSString *)name fromEventType : (NSString *)eventType) {
    NSAssert(name.length > 0, @"Collector name must not be empty");
    NSAssert(eventType.length > 0, @"Event type must not be empty");
    id<AAHAnalyticsCollector> collector = [self findRegisteredCollectorWithName:name];
    if (collector != nil) {
        [self.analyticsHub removeCollector:collector fromEventType:eventType];
    } else {
        NSLog(@"Could not remove a registered collector with name %@.", name);
    }
}

- (id)retrieveMember:(NSString *)member
                from:(NSDictionary<NSString *, id> *)dictionary
            forEvent:(NSString *)event
         withDefault:(id)defaultValue {
    id value = [dictionary objectForKey:member];
    if (value == nil) {
        NSLog(@"Member %@ was not provided for event %@.", member, event);
        return defaultValue;
    }
    return value;
}

- (AppAnalyticsHubEvent * _Nullable)eventFromDictionary:(NSDictionary<NSString *, id> *)dictionary {
    NSString * name = [dictionary objectForKey:kEventName];
    if (name == nil) {
        NSLog(@"Event name was not provided; event is unable to be recorded.");
        return nil;
    }
    
    NSString * source = [self retrieveMember:kEventSource from:dictionary forEvent:name withDefault:@""];
    NSString * type = [self retrieveMember:kEventType from:dictionary forEvent:name withDefault:@""];
    NSString * priorityString = [self retrieveMember:kEventPriority
                                                from:dictionary
                                            forEvent:name
                                         withDefault:@"normal"];
    AppAnalyticsHubEventPriority priority = [self eventPriorityFromString:priorityString forEvent:name];
    AppAnalyticsHubEvent * event = [[AppAnalyticsHubEvent alloc] initWithName:name
                                                                         source:source
                                                                           type:type
                                                                       priority:priority];
    [self addData:dictionary toEvent:event];
    [self addCounters:dictionary toEvent:event];
    [self addTimers:dictionary toEvent:event];
    [self addMetrics:dictionary toEvent:event];
    
    return event;
}

- (AppAnalyticsHubEventPriority)eventPriorityFromString:(NSString *)string forEvent:(NSString *)name {
    if ([string caseInsensitiveCompare:@"normal"] == NSOrderedSame) return AppAnalyticsHubEventPriorityNormal;
    if ([string caseInsensitiveCompare:@"critical"] == NSOrderedSame) return AppAnalyticsHubEventPriorityCritical;
    if ([string caseInsensitiveCompare:@"high"] == NSOrderedSame) return AppAnalyticsHubEventPriorityHigh;
    NSLog(@"Provided event priority %@ for event %@ is not a supported priority type. "
          "Defaulting to normal priority.",
          string, name);
    return AppAnalyticsHubEventPriorityNormal;
}

- (void)addData:(NSDictionary<NSString *, id> *)dictionary toEvent:(AppAnalyticsHubEvent *)event {
    NSDictionary<NSString *, NSString *> * data = [self retrieveMember:kEventData
                                                                  from:dictionary
                                                              forEvent:event.name
                                                           withDefault:@{}];
    [data enumerateKeysAndObjectsUsingBlock:^(NSString * _Nonnull key, NSString * _Nonnull obj, BOOL * _Nonnull stop) {
        [event addData:obj forKey:key];
    }];
}

- (void)addCounters:(NSDictionary<NSString *, id> *)dictionary toEvent:(AppAnalyticsHubEvent *)event {
    NSDictionary<NSString *, NSNumber *> * counters = [self retrieveMember:kEventCounters
                                                                      from:dictionary
                                                                  forEvent:event.name
                                                               withDefault:@{}];
    [counters enumerateKeysAndObjectsUsingBlock:^(
                                                  NSString * _Nonnull key, NSNumber * _Nonnull obj, BOOL * _Nonnull stop) {
        [event addCounterForKey:key initialCount:obj];
    }];
}

- (void)addTimers:(NSDictionary<NSString *, id> *)dictionary toEvent:(AppAnalyticsHubEvent *)event {
    NSDictionary<NSString *, NSNumber *> * timers = [self retrieveMember:kEventTimers
                                                                    from:dictionary
                                                                forEvent:event.name
                                                             withDefault:@{}];
    [timers enumerateKeysAndObjectsUsingBlock:^(
                                                NSString * _Nonnull key, NSNumber * _Nonnull obj, BOOL * _Nonnull stop) {
        [event addTimerForKey:key timeElapsed:obj];
    }];
}

- (void)addMetrics:(NSDictionary<NSString *, id> *)dictionary toEvent:(AppAnalyticsHubEvent *)event {
    NSDictionary<NSString *, id> * metrics = [self retrieveMember:kEventMetrics
                                                             from:dictionary
                                                         forEvent:event.name
                                                      withDefault:@{}];
    [metrics enumerateKeysAndObjectsUsingBlock:^(NSString * _Nonnull key, id _Nonnull obj, BOOL * _Nonnull stop) {
        [event addMetric:obj forKey:key];
    }];
}

- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}


@end
  
