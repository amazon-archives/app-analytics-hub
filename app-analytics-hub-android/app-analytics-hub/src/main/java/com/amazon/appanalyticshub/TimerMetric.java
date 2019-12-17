/*
 * Created by thontn on 8/7/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */

package com.amazon.appanalyticshub;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * <p>
 * This is the timer metric object which contains the functionality of starting,
 * ending and recording of the timer.
 * The recording of this Timer metric is provided in this object only so that
 * Event object need not know or understand this TimerMetric object.
 * </p>
 */
public class TimerMetric {
    private double startTime;
    private double totalTime;
    private String name;
    private Event parentEvent;

    public TimerMetric(@NonNull String name) {
        this(name, null);
    }

    public TimerMetric(@NonNull String name, @Nullable Event parentEvent) {
        this.name = name;
        this.parentEvent = parentEvent;
        startTime = 0;
        totalTime = 0;
    }

    /**
     * Starts the timer if not already started.
     */
    public void startTimer() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * Stops the timer if started. This method does not record the timer as
     * developer would like to start the timer again.
     * <p>
     * Will change it to end and record if needed.
     */
    public void stopTimer() {
        if (startTime > 0) {
            double sessionTime = System.currentTimeMillis() - startTime;
            totalTime += sessionTime;
            startTime = 0;
        } else {
            Log.e(Constants.TAG, "Trying to stop a timer without starting it.");
        }
    }

    /**
     * Stops and records the timer in the parent event by calling the incrementTimer method.
     *
     * @return the current time metric.
     */
    public TimerMetric recordTimer() {
        this.stopTimer();
        if (parentEvent != null) {
            parentEvent.removeTimer(this.name);
            parentEvent.addTimer(this.name, totalTime);
        } else {
            Log.e(Constants.TAG, "Could not log this metric as no parent event is defined.");
        }
        return this;
    }

    /**
     * Records the timer in the events that are passed by calling the
     * incrementTimer method.
     *
     * @param events in which this timer metric has to be recorded
     */
    public void recordTimerInEvents(@NonNull Event... events) {
        this.stopTimer();
        for (Event event : events) {
            event.removeTimer(this.name);
            event.addTimer(this.name, totalTime);
        }
    }
}
