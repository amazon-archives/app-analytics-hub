//
//  AppAnalyticsHubEvent.h
//  AppAnalyticsHubiOS
//
//  Created by Lykhonis, Volodymyr on 11/5/18.
//  Copyright © 2018 Lykhonis, Volodymyr. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "OCLint.h"

NS_ASSUME_NONNULL_BEGIN

/**
 The priority of an event. Classifies ranking of urgency between events.

 - AppAnalyticsHubEventPriorityHigh: High priority
 - AppAnalyticsHubEventPriorityNormal: Normal priority
 - AppAnalyticsHubEventPriorityCritical: Critical priority
 */
typedef NS_ENUM(NSInteger, AppAnalyticsHubEventPriority) {
    AppAnalyticsHubEventPriorityHigh NS_SWIFT_NAME(high),
    AppAnalyticsHubEventPriorityNormal NS_SWIFT_NAME(normal),
    AppAnalyticsHubEventPriorityCritical NS_SWIFT_NAME(critical)
} NS_SWIFT_NAME(EventPriority);

typedef NSString * AppAnalyticsHubEventType NS_TYPED_EXTENSIBLE_ENUM NS_SWIFT_NAME(EventType);
FOUNDATION_EXPORT OCLINT_SUPRESS AppAnalyticsHubEventType const AppAnalyticsHubEventTypeOperational;
FOUNDATION_EXPORT OCLINT_SUPRESS AppAnalyticsHubEventType const AppAnalyticsHubEventTypeEngagement;

/**
 An event aggregates data to be recorded in a collector.
 */
NS_SWIFT_NAME(Event)
@interface AppAnalyticsHubEvent : NSObject

@property(nonatomic, readonly) NSString * name;
@property(nonatomic, readonly) NSString * source;
@property(nonatomic, readonly) NSString * type;
@property(nonatomic, readonly) AppAnalyticsHubEventPriority priority;
@property(nonatomic, readonly) NSDictionary<NSString *, NSString *> * data;
@property(nonatomic, readonly) NSDictionary<NSString *, NSNumber *> * counters;
@property(nonatomic, readonly) NSDictionary<NSString *, NSNumber *> * timers;
@property(nonatomic, readonly) NSDictionary<NSString *, id> * metrics;

/**
 Initializes a new event with the provided parameters. A priority of the event defaults to normal.

 @param name The name to distinguish this event.
 @param source The source this event originitated from.
 @param type The type of event this is (e.g. operational, engagement, etc.)
 @return New event instance.
 */
- (instancetype)initWithName:(NSString *)name source:(NSString *)source type:(AppAnalyticsHubEventType)type;

/**
 Initializes a new event with the provided parameters.

 @param name The name to distinguish this event.
 @param source The source this event originitated from.
 @param type The type of event this is (e.g. operational, engagement, etc.)
 @param priority The priority of this event.
 @return New event instance.
 */
- (instancetype)initWithName:(NSString *)name
                      source:(nullable NSString *)source
                        type:(AppAnalyticsHubEventType)type
                    priority:(AppAnalyticsHubEventPriority)priority;
/**
 Initializes a new event with the provided parameters.
 
 @param name The name to distinguish this event.
 @param type The type of event this is (e.g. operational, engagement, etc.)
 @return New event instance.
 */
- (instancetype)initWithName:(NSString *)name type:(AppAnalyticsHubEventType)type;
/**
 Adds data to this event.

 @param value The data to add to the event.
 @param key The key to distinguish this data.
 */
- (void)addData:(NSString *)value forKey:(NSString *)key;

/**
 Removes data from this event.

 @param key The key for the data to remove.
 */
- (void)removeDataForKey:(NSString *)key;

/**
 Adds a counter to this event.

 @param key The key to distinguish this counter.
 */
- (void)addCounterForKey:(NSString *)key;

/**
 Adds a counter to this event.

 @param key The key to distinguish this counter.
 @param initialCount The initial count to start this counter at.
 */
- (void)addCounterForKey:(NSString *)key initialCount:(NSNumber *)initialCount;

/**
 Increments the previously added counter's total count by the specified value.

 @param key The key of the counter to increment.
 */
- (void)incrementCounterForKey:(NSString *)key;

/**
 Increments the previously added counter's total count by the specified value.

 @param key The key of the counter to increment.
 @param delta The amount by which to increment this counter.
 */
- (void)incrementCounterForKey:(NSString *)key by:(NSNumber *)delta;

/**
 Removes a counter from this event.

 @param key The key of the counter to remove.
 */
- (void)removeCounterForKey:(NSString *)key;

/**
 Adds a timer to this event.

 @param key The key to distinguish this timer.
 @param timeElapsed The total time already elapsed for this timer.
 */
- (void)addTimerForKey:(NSString *)key timeElapsed:(NSNumber *)timeElapsed;

/**
 Increments the previously added timer's elapsed time by the specified value.

 @param key The key of the counter to increment.
 @param delta The amount by which to increment this timer.
 */
- (void)incrementTimerForKey:(NSString *)key by:(NSNumber *)delta;

/**
 Removes a timer from this event.

 @param key The key of the timer to remove.
 */
- (void)removeTimerForKey:(NSString *)key;

// TODO: Document to showcase how to add BOOL via NSNumber and other types

/**
 Adds a custom metric to this event.

 To add primitive types such as int or BOOL use [NSNumber numberWithBool:(BOOL)] and such.

 @param value The metric's value to add to the event.
 @param key The key to distinguish this metric.
 */
- (void)addMetric:(id)value forKey:(NSString *)key;

/**
 Removes a custom metric from this event.

 @param key The key of the metric to remove.
 */
- (void)removeMetricForKey:(NSString *)key;

@end

NS_ASSUME_NONNULL_END
