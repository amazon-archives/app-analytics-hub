//
//  AnalyticsHubTimerMetric.h
//  AppAnalyticsHubiOS
//
//  Created by Lykhonis, Volodymyr on 11/5/18.
//  Copyright © 2018 Lykhonis, Volodymyr. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AppAnalyticsHubEvent.h"

NS_ASSUME_NONNULL_BEGIN

/**
 Basic timer class that measures elapsed time for processes.
 */
NS_SWIFT_NAME(TimerMetric)
@interface AnalyticsHubTimerMetric : NSObject

/**
 Initializes this timer with the provided name.

 @param name The name to distinguish this timer.
 @param event The event this timer originated from.
 @return Instance of timer metric
 */
- (instancetype)initWithName:(NSString *)name parent:(nullable AppAnalyticsHubEvent *)event;

/**
 Starts the timer if it is not already running.
 */
- (void)start;

/**
 Stops the timer if it is running.
 */
- (void)stop;

/**
 Stops the timer and then records the total elapsed time this timer's parent event.
 */
- (void)record;

/**
 Stops the timer and then records the total elapsed time to the provided events.

 @param event The events to record this timer's elapsed time to.
 */
- (void)recordInEvents:(AppAnalyticsHubEvent *)event, ...;

/**
 Stops the timer and then records the total elapsed time to the provided events.

 @param events The events to record this timer's elapsed time to.
 */
- (void)recordInEventsArray:(NSArray<AppAnalyticsHubEvent *> *)events;

@end

NS_ASSUME_NONNULL_END
