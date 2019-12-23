/*
 * Created by thontn on 8/8/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */
package com.amazon.appanalyticshub;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * <p>
 * Analytics Hub Interface which would be implemented by the {@link AppAnalyticsHub} class.
 * </p>
 */
public interface AnalyticsHub {

    /**
     * Sets the default Analytics collector({@link AnalyticsCollector}).
     *
     * @param defaultAnalyticsCollector default collector for all events.
     */
    void setDefaultAnalyticsCollector(AnalyticsCollector defaultAnalyticsCollector);

    /**
     * This method would remove the given analytics collector if registered.
     *
     * @param eventType event type to unlink the collector from.
     * @param collector collector to unlink.
     */
    void removeCollectorFromEventType(String eventType, AnalyticsCollector collector);

    /**
     * This method would add the given analytics collector if not registered.
     *
     * @param eventType event type to link the collector.
     * @param collector collector to link.
     */
    void addCollectorToEventType(EventType eventType, AnalyticsCollector collector);

    /**
     * This method would remove the given analytics collector if registered.
     *
     * @param eventType event type to unlink the collector from.
     * @param collector collector to unlink.
     */
    void removeCollectorFromEventType(EventType eventType, AnalyticsCollector collector);

    /**
     * Returns all the collectors for the given event type.
     *
     * @param eventType event type to all the collectors.
     * @return list of linked collectors.
     */
    List<AnalyticsCollector> getCollectors(String eventType);

    /**
     * Returns all the collectors for the given event type.
     *
     * @param eventType event type to all the collectors.
     * @return list of lined collectors
     */
    List<AnalyticsCollector> getCollectors(EventType eventType);

    /**
     * Registers a analytics collector for the name. For 1 name only 1 analytics
     * collector can be registered and cannot be overridden.
     *
     * @param collector collector to register.
     */
    void registerCollector(AnalyticsCollector collector);

    /**
     * This method would send the event to be recorded the respective registered analytics
     * collectors based on the event type.
     *
     * @param event event to be recorded.
     */
    void recordEvent(Event event);

    /**
     * Gets names of all the registered collectors.
     *
     * @return name of all registered collectors
     */
    String[] getRegisteredCollectorsNames();

    /**
     * This method would add the given analytics collector if not registered.
     *
     * @param eventType event type to link the collector.
     * @param collector collector to link.
     */
    void addCollectorToEventType(String eventType, AnalyticsCollector collector);

    /**
     * Adds a registered collector to the respective event type.
     *
     * @param eventType     event type to link the collector.
     * @param collectorName collector name of a registered collector to link.
     */
    void addRegisteredCollectorToEventType(@NonNull String eventType, @NonNull String collectorName);

    /**
     * Removes a registered collector to the respective event type.
     *
     * @param eventType     event type to unlink the collector from.
     * @param collectorName collector name of a registered collector to unlink.
     */
    void removeRegisteredCollectorFromEventType(@NonNull String eventType, @NonNull String collectorName);

}
