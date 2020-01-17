//
//  AppDelegate.swift
//  AppAnalyticsHubSample
//
//  Created by Thontepu, Naveen on 13/01/20.
//  Copyright © 2020 amazon. All rights reserved.
//

import UIKit

@UIApplicationMain
class AppDelegate: UIResponder, UIApplicationDelegate {



    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application launch.
        let appAnalyticsHub = AppAnalyticsHub.shared;
        appAnalyticsHub.defaultCollector = PrintAnalyticsCollector()
        appAnalyticsHub.register(PrintAnalyticsCollector())
        let event = Event(name: "App Start", source: "App Delegate", type: EventType.engagement)
        event.addData("data", forKey: "dataKey")
        event.addCounter(forKey: "counterKey", initialCount: 1)
        appAnalyticsHub.record(event)
        return true
    }

    // MARK: UISceneSession Lifecycle

    func application(_ application: UIApplication, configurationForConnecting connectingSceneSession: UISceneSession, options: UIScene.ConnectionOptions) -> UISceneConfiguration {
        // Called when a new scene session is being created.
        // Use this method to select a configuration to create the new scene with.
        return UISceneConfiguration(name: "Default Configuration", sessionRole: connectingSceneSession.role)
    }

    func application(_ application: UIApplication, didDiscardSceneSessions sceneSessions: Set<UISceneSession>) {
        // Called when the user discards a scene session.
        // If any sessions were discarded while the application was not running, this will be called shortly after application:didFinishLaunchingWithOptions.
        // Use this method to release any resources that were specific to the discarded scenes, as they will not return.
    }
    class PrintAnalyticsCollector: NSObject ,AnalyticsCollector {
        
        var name: String = "PrintAnalyticsCollector"
        
        func record(_ event: Event) {
            print("the event = ",event.name, "\nsource = ",event.source, "\ntype = ",event.type)
        }
        
    }
}

