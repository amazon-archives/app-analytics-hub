/*
 * Created by thontn on 8/7/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */
package com.amazon.appanalyticshub;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * Event class which would be used by for recording in analytics collector.
 * </p>
 */
public class Event {
    /**
     * The name distinguishing this event.
     */
    private String name;

    /**
     * The source of the event.
     */
    private String source;

    /**
     * Type of the event can be anything that the developer can set or
     * the predefined types that we created.
     */
    private String eventType;

    /**
     * Priority of the event
     */
    private Priority priority;

    /**
     * Any additional data/attributes to be emitted along with this event.
     * Limiting this as a string-string key-value pair as analytics
     * solutions accepts only strings.
     */
    private Map<String, String> data;
    /**
     * Variable to store all the counter metrics linked to this Event.
     */
    private Map<String, Double> counters;
    /**
     * Variable to store all the timer metrics linked to this Event.
     */
    private Map<String, Double> timers;
    /**
     * Variable to store all the custom metrics linked to this Event.
     */
    private Map<String, Object> metrics;

    /**
     * Creates an event with the given name, source, event type and Normal priority.
     *
     * @param name      of the event.
     * @param source    of the event.
     * @param eventType of the event.
     */
    public Event(@NonNull String name, @Nullable String source, @NonNull String eventType) {
        this(name, source, eventType, Priority.NORMAL);
    }

    /**
     * Creates an event with the given name, source, event type and priority.
     *
     * @param name      of the event.
     * @param source    of the event.
     * @param eventType of the event.
     * @param priority  of the event.
     */
    public Event(@NonNull String name, @Nullable String source, @NonNull String eventType, @Nullable Priority priority) {
        this.name = name;
        this.source = source;
        this.eventType = eventType;
        this.priority = priority != null ? priority : Priority.NORMAL;
        data = new HashMap<>();
        counters = new HashMap<>();
        timers = new HashMap<>();
        metrics = new HashMap<>();

    }

    /**
     * Adds Data to the event.
     *
     * @param name  of the data.
     * @param value of the data.
     * @return the current event.
     */
    public Event addData(@NonNull String name, @NonNull String value) {
        data.put(name, value);
        return this;
    }

    /**
     * Removes data from the event.
     *
     * @param name of the data.
     * @return the current event.
     */
    public Event removeData(@NonNull String name) {
        if (!data.containsKey(name)) {
            Log.w(Constants.TAG, "Trying to remove non existent data.");
            return this;
        }
        data.remove(name);
        return this;
    }

    /**
     * Adds a counter metric to the event with the value provided.
     *
     * @param name  of the counter
     * @param count for the counter
     * @return the current event.
     */
    public Event addCounter(@NonNull String name, double count) {
        return this.incrementCounter(name, count);
    }

    /**
     * Increments the counter metric with the value provided. If the counter doesn't exist then it
     * would be created.
     *
     * @param name        of the counter
     * @param incrementBy value by which the current value has to be incremented.
     * @return the current event.
     */
    public Event incrementCounter(@NonNull String name, double incrementBy) {
        Double value = this.counters.get(name);
        if (value == null) {
            value = 0d;
            Log.w(Constants.TAG, "Creating counter as it doesn't exist.");
        }
        value += incrementBy;
        this.counters.put(name, value);
        return this;
    }

    /**
     * Increments the counter metric with 1. If the counter doesn't exist then it
     * would be created.
     *
     * @param name of the counter
     * @return the current event.
     */
    public Event incrementCounter(@NonNull String name) {
        return this.incrementCounter(name, 1);
    }

    /**
     * Removes the counter.
     *
     * @param name of the counter
     * @return the current event.
     */
    public Event removeCounter(@NonNull String name) {
        this.counters.remove(name);
        return this;
    }

    /**
     * Adds a Timer metric to the event with the time value provided.
     *
     * @param name of the timer
     * @param time for the timer
     * @return the current event.
     */
    public Event addTimer(@NonNull String name, double time) {
        return this.incrementTimer(name, time);
    }

    /**
     * Increments Timer metric with the provided value. If the timer doesn't exist then it
     * would be created.
     *
     * @param name        of the timer
     * @param incrementBy value by which the current value has to be incremented.
     * @return the current event.
     */
    public Event incrementTimer(String name, double incrementBy) {
        Double value = this.timers.get(name);
        if (value == null) {
            value = 0d;
            Log.w(Constants.TAG, "Creating timer as it doesn't exist.");
        }
        value += incrementBy;
        this.timers.put(name, value);
        return this;
    }

    /**
     * Adds a custom metric to the event.
     *
     * @param name  of the metric
     * @param value of the metric
     * @return the current event.
     */
    public Event addMetric(String name, Object value) {
        if (value instanceof String || value instanceof Boolean) {
            this.metrics.put(name, value);
        } else {
            Log.e(Constants.TAG, "Metric not added as the value is not a string or boolean");
        }
        return this;
    }

    /**
     * Removes the timer.
     *
     * @param name of the timer
     * @return the current event.
     */
    public Event removeTimer(String name) {
        this.timers.remove(name);
        return this;
    }

    /**
     * Removes the Metric.
     *
     * @param name of the metric
     * @return the current event.
     */
    public Event removeMetric(String name) {
        return this;
    }

    /**
     * Gets name of the event
     *
     * @return the event name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets source of the event
     *
     * @return the event source.
     */
    public String getSource() {
        return source;
    }

    /**
     * Gets event type of the event
     *
     * @return the event type.
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Gets priority of the event
     *
     * @return the event priority.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Sets priority of the event.
     */
    public void setPriority(@NonNull Priority priority) {
        this.priority = priority;
    }

    /**
     * Gets data of the event
     *
     * @return the event's data.
     */
    public Map<String, String> getData() {
        return data;
    }

    /**
     * Gets counters of the event
     *
     * @return the event's counter
     */
    public Map<String, Double> getCounters() {
        return counters;
    }

    /**
     * Gets timers of the event
     *
     * @return the event's timer
     */
    public Map<String, Double> getTimers() {
        return timers;
    }

    /**
     * Gets metrics of the event
     *
     * @return the event's metrics.
     */
    public Map<String, Object> getMetrics() {
        return metrics;
    }

    @NonNull
    @Override
    public String toString() {
        return "name = " + name +
                " source = " + source +
                " eventType = " + eventType +
                " priority = " + priority +
                " data = " + data +
                " counters = " + counters +
                " timers = " + timers +
                " metrics = " + metrics;
    }
}
