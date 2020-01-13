//
//  AnalyticsCollector.h
//  AppAnalyticsHubiOS
//
//  Created by Thontepu, Naveen on 03/12/19.
//  Copyright © 2019 Thontepu, Naveen. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AppAnalyticsHubEvent.h"

NS_ASSUME_NONNULL_BEGIN

NS_SWIFT_NAME(AnalyticsCollector)
@protocol AnalyticsCollector <NSObject>

@property(nonatomic, readonly) NSString * name;

- (void)recordEvent:(AppAnalyticsHubEvent *)event;

@end

NS_ASSUME_NONNULL_END
