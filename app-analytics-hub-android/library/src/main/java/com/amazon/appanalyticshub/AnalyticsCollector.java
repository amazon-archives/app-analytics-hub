/*
 * Created by thontn on 8/7/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */

package com.amazon.appanalyticshub;

import androidx.annotation.NonNull;

/**
 * <p>
 * The interface which would be implemented by the different Analytics Collectors.
 * </p>
 */
public interface AnalyticsCollector {

    /**
     * Returns the name of the Analytics Collector.
     *
     * @return the name of the collector.
     */
    String getName();

    /**
     * Records the event sent to the collector.
     *
     * @param event to be recorded.
     */
    void recordEvent(@NonNull Event event);
}
