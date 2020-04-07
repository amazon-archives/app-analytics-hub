//
//  Created by Naveen Thontepu  on 11/6/18.
//  Copyright © 2018 Amazon. All rights reserved.
//

#import <Foundation/Foundation.h>
#if __has_include("RCTBridgeModule.h")
#import "RCTBridgeModule.h"
#else
#import <React/RCTBridgeModule.h>
#endif

#if __has_include("AppAnalyticsHubiOS.h")
#import "AppAnalyticsHubiOS.h"
#else
#import <AppAnalyticsHubiOS/AppAnalyticsHubiOS.h>
#endif

@interface AppAnalyticsHubReactNative : NSObject <RCTBridgeModule>

@end
  
