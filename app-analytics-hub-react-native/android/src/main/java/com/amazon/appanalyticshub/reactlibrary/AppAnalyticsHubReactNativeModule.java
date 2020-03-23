/*
 * Created by thontn on 8/7/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */

package com.amazon.appanalyticshub.reactlibrary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.amazon.appanalyticshub.AnalyticsHub;
import com.amazon.appanalyticshub.Event;
import com.amazon.appanalyticshub.Priority;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;

public class AppAnalyticsHubReactNativeModule extends ReactContextBaseJavaModule {

    private final AnalyticsHub analyticsHub;

    public AppAnalyticsHubReactNativeModule(ReactApplicationContext reactContext, @NonNull AnalyticsHub analyticsHub) {
        super(reactContext);
        this.analyticsHub = analyticsHub;
    }

    @Override
    public String getName() {
        return "AppAnalyticsHub";
    }

    @ReactMethod
    public void recordEvent(ReadableMap readableMap) {
        if (readableMap != null) {
            Event event = convertReadableMapToEvent(readableMap);
            if (event != null) {
                analyticsHub.recordEvent(event);
            }
        } else {
            Log.w(Constants.TAG, "Cannot record a NULL event");
        }
    }

    @ReactMethod
    public void addCollectorToEventType(String eventType, String collectorName) {
        if (!TextUtils.isEmpty(eventType) && !TextUtils.isEmpty(collectorName)) {
            analyticsHub.addRegisteredCollectorToEventType(eventType, collectorName);
        } else {
            String message = TextUtils.isEmpty(eventType) ? "Event type cannot be empty." :
                    "Collector Name cannot be empty.";
            Log.w(Constants.TAG, message);
        }
    }

    @ReactMethod
    public void removeCollectorFromEventType(String eventType, String collectorName) {
        if (!TextUtils.isEmpty(eventType) && !TextUtils.isEmpty(collectorName)) {
            analyticsHub.removeRegisteredCollectorFromEventType(eventType, collectorName);
        } else {
            String message = TextUtils.isEmpty(eventType) ? "Event type cannot be empty." :
                    "Collector Name cannot be empty.";
            Log.w(Constants.TAG, message);
        }
    }

    /**
     * Converts the Readable Map/JS Event to Event.class so that ARTNativeMetrics can record the
     * event.
     *
     * @param readableMap to convert
     * @return event
     */
    @Nullable
    private Event convertReadableMapToEvent(ReadableMap readableMap) {
        String name, source = "", eventType;
        try {
            name = readableMap.getString("name");
        } catch (Exception e) {
            Log.w(Constants.TAG, "Event Name does not exist, so unable to record event.");
            return null;
        }
        try {
            source = readableMap.getString("source");
        } catch (Exception e) {
            Log.w(Constants.TAG, "Source does not exist");
        }
        try {
            eventType = readableMap.getString("eventType");
        } catch (Exception e) {
            eventType = Constants.UNKNOWN_TYPE;
            Log.w(Constants.TAG, "Event Type does not exist");
        }
        Event event = new Event(name,
                source,
                eventType);
        try {
            event.setPriority(Priority.valueOf(readableMap.getString("priority")));
        } catch (Exception e) {
            Log.w(Constants.TAG, "Priority does not exist");
        }

        try {
            ReadableMap dataReadableMap = readableMap.getMap("data");
            if (dataReadableMap != null) {
                ReadableMapKeySetIterator dataKeyIterator = dataReadableMap.keySetIterator();
                while (dataKeyIterator.hasNextKey()) {
                    String key = dataKeyIterator.nextKey();
                    String value = dataReadableMap.getString(key);
                    if (!TextUtils.isEmpty(value)) {
                        event.addData(key, value);
                    } else {
                        Log.w(Constants.TAG, key + " data value provided is not a string.");
                    }
                }
            }
        } catch (Exception e) {
            Log.w(Constants.TAG, "Data does not exist.");
        }
        try {
            ReadableMap countersReadableMap = readableMap.getMap("counters");
            if (countersReadableMap != null) {
                ReadableMapKeySetIterator counterKeyIterator = countersReadableMap.keySetIterator();
                while (counterKeyIterator.hasNextKey()) {
                    String key = counterKeyIterator.nextKey();
                    double value = countersReadableMap.getDouble(key);
                    event.addCounter(key, value);
                }
            }
        } catch (Exception e) {
            Log.w(Constants.TAG, "Counters don't exist.");
        }
        try {
            ReadableMap timersReadableMap = readableMap.getMap("timers");
            if (timersReadableMap != null) {
                ReadableMapKeySetIterator timerKeyIterator = timersReadableMap.keySetIterator();
                while (timerKeyIterator.hasNextKey()) {
                    String key = timerKeyIterator.nextKey();
                    double value = timersReadableMap.getDouble(key);
                    event.addTimer(key, value);
                }
            }
        } catch (Exception e) {
            Log.w(Constants.TAG, "Timers don't exist.");
        }
        try {
            ReadableMap metricsReadableMap = readableMap.getMap("metrics");
            if (metricsReadableMap != null) {
                ReadableMapKeySetIterator timerKeyIterator = metricsReadableMap.keySetIterator();
                while (timerKeyIterator.hasNextKey()) {
                    String key = timerKeyIterator.nextKey();
                    String value = metricsReadableMap.getString(key);
                    if (!TextUtils.isEmpty(value)) {
                        event.addMetric(key, value);
                    } else {
                        boolean bool = metricsReadableMap.getBoolean(key);
                        event.addMetric(key, bool);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(Constants.TAG, "Metrics don't exist.");
        }
        return event;
    }
}