//
//  AppAnalyticsHub.h
//  AppAnalyticsHubiOS
//
//  Created by Lykhonis, Volodymyr on 11/6/18.
//  Copyright © 2018 Lykhonis, Volodymyr. All rights reserved.
//

#import <Foundation/Foundation.h>

#import "AAHAnalyticsHub.h"

NS_ASSUME_NONNULL_BEGIN

@interface AppAnalyticsHub : NSObject <AAHAnalyticsHub>

/**
 Shared instance of the metrics.
 */
@property(nonatomic, readonly, class) AppAnalyticsHub * shared;

- (instancetype)init NS_UNAVAILABLE;

@end

NS_ASSUME_NONNULL_END
