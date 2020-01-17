//
//  AnalyticsHubTimerMetric.m
//  AppAnalyticsHubiOS
//
//  Created by Lykhonis, Volodymyr on 11/5/18.
//  Copyright © 2018 Lykhonis, Volodymyr. All rights reserved.
//

#import "AppAnalyticsHubTimerMetric.h"

@interface AppAnalyticsHubTimerMetric ()

@property(nonatomic, nullable) NSDate * startTime;
@property(nonatomic) NSTimeInterval totalTime;
@property(nonatomic, nonnull) NSString * name;
@property(nonatomic, nullable) AppAnalyticsHubEvent * parentEvent;

@end

@implementation AppAnalyticsHubTimerMetric

- (instancetype)initWithName:(NSString *)name parent:(AppAnalyticsHubEvent *)event {
    self = [super init];
    if (self) {
        _name = name;
        _parentEvent = event;
        _totalTime = 0.0;
    }
    return self;
}

- (void)start {
    if (self.startTime != nil) {
        NSLog(@"Timer %@ is already running.", self.name);
        return;
    }
    self.startTime = [NSDate date];
}

- (void)stop {
    if (self.startTime == nil) {
        NSLog(@"Timer %@ has not been started.", self.name);
        return;
    }
    self.totalTime += [[NSDate date] timeIntervalSinceDate:self.startTime];
    self.startTime = nil;
}

- (void)record {
    [self stop];
    if (self.totalTime == 0) {
        NSLog(@"Timer %@ has not been started; recording 0 elapsed time.", self.name);
        return;
    }
    if (self.parentEvent == nil) {
        NSLog(@"Timer %@ does not have a parent event attached to it.", self.name);
        return;
    }
    NSNumber * timeElapsedInMillisconds = @(self.totalTime * 1000);
    [self.parentEvent addTimerForKey:_name timeElapsed:timeElapsedInMillisconds];
}

- (void)recordInEvents:(AppAnalyticsHubEvent *)event, ... {
    NSMutableArray<AppAnalyticsHubEvent *> * events = [NSMutableArray array];
    [events addObject:event];
    va_list args;
    va_start(args, event);
    id arg = nil;
    while ((arg = va_arg(args, id))) {
        [events addObject:arg];
    }
    va_end(args);
    [self recordInEventsArray:events];
}

- (void)recordInEventsArray:(NSArray<AppAnalyticsHubEvent *> *)events {
    [self stop];
    if (_totalTime == 0) {
        NSLog(@"Timer %@ has not been started; recording 0 elapsed time.", self.name);
    }
    NSNumber * timeElapsedInMillisconds = @(self.totalTime * 1000);
    for (AppAnalyticsHubEvent * event in events) {
        [event addTimerForKey:self.name timeElapsed:timeElapsedInMillisconds];
    }
}

@end
