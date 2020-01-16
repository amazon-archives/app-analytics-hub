//
//  AppAnalyticsHub.m
//  AppAnalyticsHubiOS
//
//  Created by Lykhonis, Volodymyr on 11/6/18.
//  Copyright © 2018 Lykhonis, Volodymyr. All rights reserved.
//

#import "AppAnalyticsHub.h"

@interface AppAnalyticsHub ()

@property(nonatomic, nonnull) NSMutableDictionary<NSString *, id<AAHAnalyticsCollector>> * collectors;
@property(nonatomic, nonnull) NSMutableDictionary<AppAnalyticsHubEventType, NSMutableSet<NSString *> *> * eventTypes;

@end

@implementation AppAnalyticsHub

@synthesize defaultCollector;

- (instancetype)init {
    self = [super init];
    if (self) {
        _collectors = [NSMutableDictionary dictionary];
        _eventTypes = [NSMutableDictionary dictionary];
    }
    return self;
}

/**
 Intercepts shared instance access to enable testing and other magical experiences.

 @param instance A shared instance.
 @return An instance of metrics.
 */
+ (AppAnalyticsHub *)interceptInstance:(AppAnalyticsHub *)instance {
    return instance;
}

+ (AppAnalyticsHub *)shared {
    static dispatch_once_t token;
    static AppAnalyticsHub * instance;
    dispatch_once(&token, ^{
        instance = [self new];
    });
    return [self interceptInstance:instance];
}

- (NSArray<id<AAHAnalyticsCollector>> *)registeredCollectors {
    return [NSArray arrayWithArray:self.collectors.allValues];
}

- (void)registerCollector:(id<AAHAnalyticsCollector>)collector {
    [self.collectors setObject:collector forKey:collector.name];
}

- (void)unregisterCollector:(id<AAHAnalyticsCollector>)collector {
    [self.collectors removeObjectForKey:collector.name];
}

- (void)addCollector:(id<AAHAnalyticsCollector>)collector toEventType:(AppAnalyticsHubEventType)eventType {
    [self registerCollector:collector];
    NSMutableSet<NSString *> * collectors = [self.eventTypes objectForKey:eventType];
    if (collectors == nil) {
        collectors = [NSMutableSet set];
        [self.eventTypes setObject:collectors forKey:eventType];
    }
    [collectors addObject:collector.name];
}

- (void)removeCollector:(id<AAHAnalyticsCollector>)collector fromEventType:(AppAnalyticsHubEventType)eventType {
    NSMutableSet<NSString *> * collectors = [self.eventTypes objectForKey:eventType];
    if (collectors == nil) {
        NSLog(@"Attempting to remove collector %@ from event type %@ when no such "
               "event type has any registered collectors.",
                collector.name, eventType);
        return;
    }
    if (![collectors containsObject:collector.name]) {
        NSLog(@"Collector %@ cannot be removed from event type %@ because it was never "
               "registered with that event type.",
                collector.name, eventType);
        return;
    }
    [collectors removeObject:collector.name];
}

- (NSArray<id<AAHAnalyticsCollector>> *)getCollectorsForEventType:(AppAnalyticsHubEventType)eventType {
    NSMutableArray<id<AAHAnalyticsCollector>> * collectors = [NSMutableArray array];
    NSSet<NSString *> * collectorNames = [self.eventTypes objectForKey:eventType];
    if (collectorNames != nil) {
        for (NSString * name in collectorNames) {
            id<AAHAnalyticsCollector> collector = [self.collectors objectForKey:name];
            if (collector != nil) {
                [collectors addObject:collector];
            }
        }
    }
    return collectors;
}

- (void)recordEvent:(AppAnalyticsHubEvent *)event {
    NSArray<id<AAHAnalyticsCollector>> * eventCollectors =
            [self getCollectorsForEventType:[event.type lowercaseString]];
    NSMutableSet<id<AAHAnalyticsCollector>> * collectors = [NSMutableSet setWithArray:eventCollectors];
    if (self.defaultCollector != nil) {
        [collectors addObject:self.defaultCollector];
    }
    for (id<AAHAnalyticsCollector> collector in collectors) {
        [collector recordEvent:event];
    }
}

@end
