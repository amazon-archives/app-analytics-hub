/*
 * Created by thontn on 8/22/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */
package com.amazon.appanalyticshub;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * <p>
 * Event Factory is used to create multiple events from same source and event type.
 * </p>
 */
public class EventFactory {

    private String source;
    private String eventType;

    public EventFactory() {
    }

    /**
     * Creates an Event Factory object for the given a source and event type.
     *
     * @param source    of all the event being created by this factory
     * @param eventType of all the event being created by this factory
     */
    public EventFactory(@Nullable String source, @Nullable String eventType) {
        this.source = source;
        this.eventType = eventType;
    }

    /**
     * Creates an Event Factory object for the given a source and event type.
     *
     * @param source    of all the event being created by this factory
     * @param eventType of all the event being created by this factory
     */
    public EventFactory(@Nullable String source, @NonNull EventType eventType) {
        this(source, eventType.name());
    }

    /**
     * Method to create a new Event with the provided name, source, priority and event type.
     * For creating an event, event type is mandatory so either provide the event
     * type in factory constructor or event factory event data.
     *
     * @param name      - mandatory field for event creation
     * @param source    - optional field for event creation
     * @param priority  - optional field for event creation
     * @param eventType - mandatory field for event creation
     * @return event
     */
    public Event createEvent(@NonNull String name,
                             @Nullable String source,
                             @Nullable Priority priority,
                             @NonNull EventType eventType) {
        return this.createEvent(name, source, priority, eventType.name());
    }

    /**
     * Method to create a new Event with the provided name, source, priority and event type.
     * For creating an event, event type is mandatory so either provide the event
     * type in factory constructor or event factory event data.
     *
     * @param name      - mandatory field for event creation
     * @param source    - optional field for event creation
     * @param priority  - optional field for event creation
     * @param eventType - mandatory field for event creation. Either event factory should have a
     *                  default event type or you have to provide it in this method.
     * @return event
     */
    public Event createEvent(@NonNull String name,
                             @Nullable String source,
                             @Nullable Priority priority,
                             @Nullable String eventType) {
        source = !TextUtils.isEmpty(source) ? source : this.source;
        eventType = !TextUtils.isEmpty(eventType) ? eventType : this.eventType;
        if (TextUtils.isEmpty(eventType)) {
            eventType = Constants.UNKNOWN_TYPE;
        }
        return new Event(name, source, eventType, priority);
    }

    /**
     * Method to create a new Event with the provided name.
     * For creating an event, event type is mandatory so either provide the event
     * type in factory constructor or event factory event data.
     *
     * @param name - mandatory field for event creation
     * @return event
     */
    public Event createEvent(@NonNull String name) {
        return this.createEvent(name, source, null, eventType);
    }

    /**
     * Method to create a new Event with the provided name and priority.
     * For creating an event, event type is mandatory so either provide the event
     * type in factory constructor or event factory event data.
     *
     * @param name     - mandatory field for event creation
     * @param priority - optional field for event creation
     * @return event
     */
    public Event createEvent(@NonNull String name,
                             @Nullable Priority priority) {
        return this.createEvent(name, source, priority, eventType);
    }
}
