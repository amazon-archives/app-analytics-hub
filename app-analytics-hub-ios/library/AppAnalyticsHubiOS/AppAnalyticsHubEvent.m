//
//  AppAnalyticsHubEvent.m
//  AppAnalyticsHubiOS
//
//  Created by Lykhonis, Volodymyr on 11/5/18.
//  Copyright © 2018 Lykhonis, Volodymyr. All rights reserved.
//

#import "AppAnalyticsHub.h"

#import "OCLint.h"

AppAnalyticsHubEventType const AppAnalyticsHubEventTypeOperational = @"operational";
AppAnalyticsHubEventType const AppAnalyticsHubEventTypeEngagement = @"engagement";

@interface AppAnalyticsHubEvent ()

@property(nonatomic, readonly) NSMutableDictionary<NSString *, NSString *> * actualData;
@property(nonatomic, readonly) NSMutableDictionary<NSString *, NSNumber *> * actualCounters;
@property(nonatomic, readonly) NSMutableDictionary<NSString *, NSNumber *> * actualTimers;
@property(nonatomic, readonly) NSMutableDictionary<NSString *, id> * actualMetrics;

@end

@implementation AppAnalyticsHubEvent

- (instancetype)initWithName:(NSString *)name source:(NSString *)source type:(AppAnalyticsHubEventType)type {
    return [self initWithName:name source:source type:type priority:AppAnalyticsHubEventPriorityNormal];
}

- (instancetype)initWithName:(NSString *)name type:(AppAnalyticsHubEventType)type {
    return [self initWithName:name source:NULL type:type priority:AppAnalyticsHubEventPriorityNormal];
}

- (instancetype)initWithName:(NSString *)name
                      source:(NSString *)source
                        type:(AppAnalyticsHubEventType)type
                    priority:(AppAnalyticsHubEventPriority)priority {
    self = [super init];
    if (self) {
        _name = name;
        _source = source;
        _type = type;
        _priority = priority;
        _actualData = [NSMutableDictionary dictionary];
        _actualCounters = [NSMutableDictionary dictionary];
        _actualTimers = [NSMutableDictionary dictionary];
        _actualMetrics = [NSMutableDictionary dictionary];
    }
    return self;
}

- (NSDictionary<NSString *, NSString *> *)data {
    return self.actualData;
}

- (NSDictionary<NSString *, NSNumber *> *)counters {
    return self.actualCounters;
}

- (NSDictionary<NSString *, NSNumber *> *)timers {
    return self.actualTimers;
}

- (NSDictionary<NSString *, id> *)metrics {
    return self.actualMetrics;
}

- (void)addData:(NSString *)value forKey:(NSString *)key {
    [self.actualData setObject:value forKey:key];
}

- (void)removeDataForKey:(NSString *)key {
    BOOL keyExists = [self.actualData objectForKey:key] != nil;
    if (keyExists) {
        [self.actualData removeObjectForKey:key];
    } else {
        NSLog(@"Cannot remove data with key %@, as it does not exist on event %@.", key, _name);
    }
}

- (void)addCounterForKey:(NSString *)key {
    [self addCounterForKey:key initialCount:@0.0];
}

- (void)addCounterForKey:(NSString *)key initialCount:(NSNumber *)initialCount {
    [self.actualCounters setObject:initialCount forKey:key];
}

- (void)incrementCounterForKey:(NSString *)key {
    [self incrementCounterForKey:key by:@1.0];
}

- (void)incrementCounterForKey:(NSString *)key by:(NSNumber *)delta {
    NSNumber * current = [self.actualCounters objectForKey:key];
    if (current != nil) {
        [self.actualCounters setObject:@(current.doubleValue + delta.doubleValue) forKey:key];
    } else {
        NSLog(@"Counter metric with key %@ does not exist on event %@. Creating a new count with value %@.", key, _name,
                delta);
        [self.actualCounters setObject:delta forKey:key];
    }
}

- (void)removeCounterForKey:(NSString *)key {
    BOOL keyExists = [self.actualCounters objectForKey:key] != nil;
    if (keyExists) {
        [self.actualCounters removeObjectForKey:key];
    } else {
        NSLog(@"Cannot remove count metric with key %@, as it does not exist on event %@.", key, _name);
    }
}

- (void)addTimerForKey:(NSString *)key timeElapsed:(NSNumber *)timeElapsed {
    [self.actualTimers setObject:timeElapsed forKey:key];
}

- (void)incrementTimerForKey:(NSString *)key by:(NSNumber *)delta {
    NSNumber * current = [self.actualTimers objectForKey:key];
    if (current != nil) {
        [self.actualTimers setObject:@(current.doubleValue + delta.doubleValue) forKey:key];
    } else {
        NSLog(@"Time metric with key %@ does not exist on event %@. Creating a new time with value %@.", key, _name,
                delta);
        [self.actualTimers setObject:delta forKey:key];
    }
}

- (void)removeTimerForKey:(NSString *)key {
    BOOL keyExists = [self.actualTimers objectForKey:key] != nil;
    if (keyExists) {
        [self.actualTimers removeObjectForKey:key];
    } else {
        NSLog(@"Cannot remove time metric with key %@, as it does not exist on event %@.", key, _name);
    }
}

- (void)addMetric:(id)value forKey:(NSString *)key {
    [self.actualMetrics setObject:value forKey:key];
}

- (void)removeMetricForKey:(NSString *)key {
    BOOL keyExists = [self.actualMetrics objectForKey:key] != nil;
    if (keyExists) {
        [self.actualMetrics removeObjectForKey:key];
    } else {
        NSLog(@"Cannot remove metric with key %@, as it does not exist on event %@.", key, _name);
    }
}

@end
