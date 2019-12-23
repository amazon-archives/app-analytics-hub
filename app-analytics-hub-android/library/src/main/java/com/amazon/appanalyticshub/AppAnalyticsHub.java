/*
 * Created by thontn on 8/6/18
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */

package com.amazon.appanalyticshub;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * AppAnalyticsHub is used to register different analytics collectors, registering analytics
 * collectors to different event type and records an event to different collectors.
 * </p>
 */
public class AppAnalyticsHub implements AnalyticsHub {

    private Map<String, Set<String>> eventTypeToCollectorMap;
    private Map<String, AnalyticsCollector> registeredCollectors;
    private AnalyticsCollector defaultAnalyticsCollector;

    public AppAnalyticsHub() {
        eventTypeToCollectorMap = new HashMap<>();
        registeredCollectors = new HashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDefaultAnalyticsCollector(AnalyticsCollector defaultAnalyticsCollector) {
        this.defaultAnalyticsCollector = defaultAnalyticsCollector;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCollectorFromEventType(@NonNull String eventType,
                                             @NonNull AnalyticsCollector collector) {
        Set<String> collectors = eventTypeToCollectorMap.get(eventType);
        if (collectors != null) {
            collectors.remove(collector.getName());
            eventTypeToCollectorMap.put(eventType, collectors);
        } else {
            Log.w(Constants.TAG, "Trying to remove collector from without registering any " +
                    "collectors");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCollectorToEventType(@NonNull EventType eventType,
                                        @NonNull AnalyticsCollector collector) {
        this.addCollectorToEventType(eventType.name(), collector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeCollectorFromEventType(@NonNull EventType eventType,
                                             @NonNull AnalyticsCollector collector) {
        this.removeCollectorFromEventType(eventType.name(), collector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalyticsCollector> getCollectors(@NonNull String eventType) {
        Set<String> collectorNames = eventTypeToCollectorMap.get(eventType);
        List<AnalyticsCollector> collectors = new ArrayList<>();
        if (collectorNames != null) {
            for (String collectorName : collectorNames) {
                collectors.add(registeredCollectors.get(collectorName));
            }
        }
        return collectors;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnalyticsCollector> getCollectors(@NonNull EventType eventType) {
        return this.getCollectors(eventType.name());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void registerCollector(@NonNull AnalyticsCollector collector) {
        registeredCollectors.put(collector.getName(), collector);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void recordEvent(@NonNull Event event) {
        String eventType = event.getEventType();
        List<AnalyticsCollector> collectors = getCollectors(eventType);
        boolean isRecordedInDefault = false;
        for (AnalyticsCollector collector : collectors) {
            if (defaultAnalyticsCollector != null && collector.getName().equals(defaultAnalyticsCollector.getName())) {
                isRecordedInDefault = true;
            }
            collector.recordEvent(event);
        }
        if (!isRecordedInDefault && defaultAnalyticsCollector != null) {
            defaultAnalyticsCollector.recordEvent(event);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getRegisteredCollectorsNames() {
        return registeredCollectors.keySet().toArray(new String[0]);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addCollectorToEventType(@NonNull String eventType, @NonNull AnalyticsCollector collector) {
        if (registeredCollectors.get(collector.getName()) == null) {
            registeredCollectors.put(collector.getName(), collector);
        }
        Set<String> collectors = eventTypeToCollectorMap.get(eventType);
        if (collectors == null) {
            collectors = new HashSet<>();
        }
        collectors.add(collector.getName());
        eventTypeToCollectorMap.put(eventType, collectors);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRegisteredCollectorToEventType(@NonNull String eventType, @NonNull String collectorName) {
        AnalyticsCollector collector = registeredCollectors.get(collectorName);
        if (collector != null) {
            this.addCollectorToEventType(eventType, collector);
        } else {
            Log.w(Constants.TAG, "Attempting to add collector " + collectorName +
                    " to event type " + eventType + " but no such collector has been registered.");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRegisteredCollectorFromEventType(@NonNull String eventType, @NonNull String collectorName) {
        AnalyticsCollector collector = registeredCollectors.get(collectorName);
        if (collector != null) {
            this.removeCollectorFromEventType(eventType, collector);
        } else {
            Log.w(Constants.TAG, "Attempting to remove collector " + collectorName +
                    " to event type " + eventType + " but no such collector has been registered.");
        }
    }
}
