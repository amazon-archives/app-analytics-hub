//
//  AnalyticsHub.h
//  AppAnalyticsHubiOS
//
//  Created by Thontepu, Naveen on 03/12/19.
//  Copyright © 2019 Thontepu, Naveen. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AnalyticsCollector.h"

NS_ASSUME_NONNULL_BEGIN

NS_SWIFT_NAME(AnalyticsHub)
@protocol AnalyticsHub <NSObject>

/**
 The default metrics collector. The metrics manager will record to this metrics collector along with any others
 passed in.
 */
@property(nonatomic, nullable) id<AnalyticsCollector> defaultCollector;

/**
 All of the registered collectors for this instance.
 */
@property(nonatomic, readonly) NSArray<id<AnalyticsCollector>> * registeredCollectors;

/**
 Records all collectors for the provided event's event type to the provided event, as well as the default collector
 if 1) it has been specified and 2) it has not already been recorded to.
 
 @param event The event to record the metrics collectors to.
 */
- (void)recordEvent:(AppAnalyticsHubEvent *)event;

/**
 Adds a metric collector to the specified custom event type.
 
 @param collector The metrics collector to add.
 @param eventType The event type to add this metrics collector to.
 */
- (void)addCollector:(id<AnalyticsCollector>)collector toEventType:(AppAnalyticsHubEventType)eventType;

/**
 Removes a metric collector from the specified custom event type.
 
 @param collector The metrics collector to remove.
 @param eventType The custom event type to remove this metrics collector from.
 */
- (void)removeCollector:(id<AnalyticsCollector>)collector fromEventType:(AppAnalyticsHubEventType)eventType;

/**
 Gets the collectors for the specified custom event type.
 
 @param eventType The custom event type to retrieve collectors from.
 @return Collectors for the event type.
 */
- (NSArray<id<AnalyticsCollector>> *)getCollectorsForEventType:(AppAnalyticsHubEventType)eventType;

/**
 Registers a collector to the metrics manager.
 
 @param collector The metrics collector to register.
 */
- (void)registerCollector:(id<AnalyticsCollector>)collector;

/**
 Unregister a collector from the metrics manager.
 
 @param collector The metrics collector to unregister.
 */
- (void)unregisterCollector:(id<AnalyticsCollector>)collector;

@end

NS_ASSUME_NONNULL_END
