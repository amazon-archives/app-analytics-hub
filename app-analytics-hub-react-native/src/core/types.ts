import { EventType, Priority } from './constants';
import { Event } from './event';

/**
 * Records metrics.
 */
export interface IMetricsRecorder {
  /**
   * Records an Event.
   * @param event The event to record.
   */
  record(event: IEvent): void;
}

/**
 * Interface to allow consumers to register JS based collectors.
 * WARNING: This interface is not stable yet.
 */
export interface ICollectorRegistry {
  /**
   * Register a collector for a given event type.
   * @param eventType
   * @param collectorName
   */
  addCollectorToEventType(
    eventType: string | EventType,
    collectorName: string
  ): void;

  /**
   * Remove a collector from a given event type.
   * @param eventType
   * @param collectorName
   */
  removeCollectorFromEventType(
    eventType: string | EventType,
    collectorName: string
  ): void;
}

/**
 * Generic Event interface
 */
export interface IEvent {
  /**
   * Adds Data to the event.
   *
   * @param {string} name
   * @param {string} value
   * @returns {IEvent}
   */
  addData(name: string, value: string): IEvent;

  /**
   * Adds a counter metric to the event.
   *
   * @param {string} name
   * @param {number} count
   * @returns {IEvent}
   */
  addCounter(name: string, count: number): IEvent;

  /**
   * Increments the counter metric with 1 as default value if not provided.
   *
   * @param {string} name
   * @param {number} incrementBy
   * @returns {IEvent}
   */
  incrementCounter(name: string, incrementBy?: number): IEvent;

  /**
   * Removes the counter.
   *
   * @param {string} name
   * @returns {IEvent}
   */
  removeCounter(name: string): IEvent;

  /**
   * Adds a Timer metric to the event with the time value provided.
   *
   * @param {string} name
   * @param {number} duration
   * @returns {IEvent}
   */
  addTimer(name: string, duration: number): IEvent;

  /**
   * Increments Timer metric with the provided value.
   *
   * @param {string} name
   * @param {number} time
   * @returns {IEvent}
   */
  incrementTimer(name: string, time: number): IEvent;

  /**
   * Adds a custom metric to the event.
   *
   * @param {string} name
   * @param {number | string | boolean} value
   * @returns {IEvent}
   */
  addMetric(name: string, value: string | boolean): IEvent;

  /**
   * Removes the timer.
   *
   * @param {string} name
   * @returns {IEvent}
   */
  removeTimer(name: string): IEvent;

  /**
   * Removes the Metric.
   *
   * @param {string} name
   * @returns {IEvent}
   */
  removeMetric(name: string): IEvent;

  /**
   * Creates an event data object and returns it.
   *
   * @returns {IEventData}
   */
  getEventData(): IEventData;
}

/**
 * Type for Event's data for storing a map of string to string.
 */
export type DataType = {
  [key: string]: string;
};

/**
 * Type for Event's metrics for storing a map of string to string or number or
 * boolean.
 */
export type MetricsType = {
  [key: string]: string | boolean;
};

/**
 * Type for storing a map of string to number.
 */
export type StringToNumberMap = {
  [key: string]: number;
};

/**
 * Type for Event's timers.
 */
export type TimersType = StringToNumberMap;

/**
 * Type for Event's counters
 */
export type CountersType = StringToNumberMap;

/**
 * Serializable Event data that is sent to the native module.
 */
export interface IEventData {
  /**
   * The name distinguishing this event.
   */
  readonly name: string;
  /**
   * The source of the event.
   */
  readonly source?: string;
  /**
   * Priority of the event.
   */
  readonly priority: string;
  /**
   * Type of the event can be anything that the developer can set or
   * the predefined types that we created.
   */
  readonly eventType: string | EventType;
  /**
   * Any additional data/attributes to be emitted along with this event.
   * Limiting this as a string-string key-value pair as analytics
   * solutions accepts only strings.
   */
  readonly data: DataType;
  /**
   * Variable to store all the counter metrics linked to this Event.
   */
  readonly counters: TimersType;
  /**
   * Variable to store all the timer metrics linked to this Event.
   */
  readonly timers: CountersType;
  /**
   * Variable to store all the custom metrics linked to this Event.
   */
  readonly metrics: MetricsType;
}

/**
 * Data Type for the createEvent method in Event Factory.
 */
export interface IEventFactoryEventData {
  /**
   * The name of the event.
   */
  readonly name: string;
  /**
   * The source of the event.
   */
  readonly source?: string;
  /**
   * The priority of this event. Optional, may be provided by factory.
   */
  readonly priority?: Priority;
  /**
   * The event type. Optional, may be provided by factory.
   */
  readonly eventType?: string | EventType;
}

/**
 * Event factory interface that would create events from the same source.
 */
export interface IEventFactory {
  /**
   * Method to create a new Event with the provided source and data.
   * For creating an event, event type is mandatory so either provide the event
   * type in factory constructor or event factory event data.
   *
   * @param {IEventFactoryEventData} eventData
   * @returns {IEvent|undefined} would return undefined if event type is not
   * present else would return a new Event instance.
   */
  createEvent(eventData: IEventFactoryEventData): Event | undefined;
}

/**
 * This is the timer metric object which contains the functionality of starting,
 * ending and recording of the timer.
 *
 * The recording of this Timer metric is provided in this object only so that
 * Event object need not know or understand this TimerMetric object.
 */
export interface ITimerMetric {
  /**
   * Starts the timer if not already started.
   */
  startTimer(): void;

  /**
   * Stops the timer if started. This method does not record the timer as
   * developer would like to resume the timer again.
   * @returns {this}
   */
  stopTimer(): this;

  /**
   * Stops and records the timer in the parent event by calling the
   * incrementTimer method.
   * @returns {this}
   */
  recordTimer(): this;

  /**
   * Stops and records the timer in the events that are passed by calling the
   * incrementTimer method.
   * @param {IEvent} events
   */
  recordTimerInEvents(...events: IEvent[]): void;
}
