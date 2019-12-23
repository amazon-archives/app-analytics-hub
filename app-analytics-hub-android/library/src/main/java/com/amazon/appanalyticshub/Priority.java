/*
 * Created by thontn on 8/7/18.
 * Copyright © 2018 Amazon.com, Inc. or its affiliates. All rights reserved.
 */

package com.amazon.appanalyticshub;

/**
 * <p>
 * This enumeration defines Priority associated with Event when
 * they are being recorded.
 * Priority is used as an indication of time sensitivity of a
 * Event. If the event collector has the functionality of priority then it would
 * be used.
 * </p>
 */
public enum Priority {
    HIGH,
    NORMAL,
    CRITICAL
}
