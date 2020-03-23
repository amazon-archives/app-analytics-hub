/*
 * Created by thontn on 8/7/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */

package com.amazon.appanalyticshub.reactlibrary;

import com.amazon.appanalyticshub.AnalyticsHub;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AppAnalyticsHubReactNativePackage implements ReactPackage {
    private final AnalyticsHub analyticsHub;

    public AppAnalyticsHubReactNativePackage(AnalyticsHub analyticsHub) {
        this.analyticsHub = analyticsHub;
    }


    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        return Collections.<NativeModule>singletonList(new AppAnalyticsHubReactNativeModule(reactContext, analyticsHub));
    }

    // Deprecated from RN 0.47
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}