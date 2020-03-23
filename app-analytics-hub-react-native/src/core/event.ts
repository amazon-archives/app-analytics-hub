import { EventType, Priority } from './constants';
import {
  CountersType,
  DataType,
  IEvent,
  IEventData,
  MetricsType,
  TimersType
} from './types';

export class Event implements IEvent {
  /**
   * The name distinguishing this event.
   */
  private readonly name: string;
  /**
   * The source of the event.
   */
  private readonly source?: string;
  /**
   * Priority of the event.
   */
  private readonly priority: Priority;
  /**
   * Type of the event can be anything that the developer can set or
   * the predefined types that we created.
   */
  private readonly type: string | EventType;
  /**
   * Any additional data/attributes to be emitted along with this event.
   * Limiting this as a string-string key-value pair as analytics
   * solutions accepts only strings.
   */
  private readonly data: DataType;
  /**
   * Variable to store all the counter metrics linked to this Event.
   */
  private readonly counters: CountersType;
  /**
   * Variable to store all the timer metrics linked to this Event.
   */
  private readonly timers: TimersType;
  /**
   * Variable to store all the custom metrics linked to this Event.
   */
  private readonly metrics: MetricsType;

  constructor(
    name: string,
    type: string | EventType,
    source?: string,
    priority?: Priority
  ) {
    this.name = name;
    this.type = type;
    this.priority = priority ? priority : Priority.NORMAL;
    this.source = source;
    this.counters = {};
    this.timers = {};
    this.metrics = {};
    this.data = {};
  }

  /**
   * Adds a counter metric to the event.
   *
   * @param {string} name
   * @param {number} count
   * @returns {IEvent}
   */
  addCounter(name: string, count: number): IEvent {
    return this.incrementCounter(name, count);
  }

  /**
   * Adds Data to the event.
   *
   * @param {string} name
   * @param {string} value
   * @returns {IEvent}
   */
  addData(name: string, value: string): IEvent {
    this.data[name] = value;
    return this;
  }

  /**
   * Adds a custom metric to the event.
   *
   * @param {string} name
   * @param {string | boolean} value
   * @returns {IEvent}
   */
  addMetric(name: string, value: string | boolean): IEvent {
    this.metrics[name] = value;
    return this;
  }

  /**
   * Adds a Timer metric to the event with the time value provided.
   *
   * @param {string} name
   * @param {number} duration
   * @returns {IEvent}
   */
  addTimer(name: string, duration: number): IEvent {
    return this.incrementTimer(name, duration);
  }

  /**
   * Creates an event data object and returns it.
   * TODO: Deepcopy? as react will freeze the data when they are sent to the bridge,
   * or should we not care about an event after its recorded ?
   *
   * @returns {IEventData}
   */
  getEventData(): IEventData {
    return {
      counters: this.counters,
      data: this.data,
      metrics: this.metrics,
      name: this.name,
      priority: Priority[this.priority],
      source: this.source,
      timers: this.timers,
      eventType: this.type
    };
  }

  /**
   * Increments the counter metric with 1 as default value if not provided. If
   * a counter doesn't exist it would be created.
   *
   * @param {string} name
   * @param {number} incrementBy
   * @returns {IEvent}
   */
  incrementCounter(name: string, incrementBy?: number): IEvent {
    const incrementByValue =
      incrementBy !== undefined && incrementBy !== null ? incrementBy : 1;
    let value = this.counters[name];
    if (!value) {
      value = 0;
    }
    value += incrementByValue;
    this.counters[name] = value;
    return this;
  }

  /**
   * Increments Timer metric with the provided value. If the timer doesn't exist
   * then it would be created.
   *
   * @param {string} name
   * @param {number} time
   * @returns {IEvent}
   */
  incrementTimer(name: string, time: number): IEvent {
    let finalValue = this.timers[name];
    if (!finalValue) {
      finalValue = 0;
    }
    finalValue = finalValue ? finalValue + time : time;
    this.timers[name] = finalValue;
    return this;
  }

  /**
   * Removes the counter.
   *
   * @param {string} name
   * @returns {IEvent}
   */
  removeCounter(name: string): IEvent {
    delete this.counters[name];
    return this;
  }

  /**
   * Removes the data.
   *
   * @param name
   */
  removeData(name: string): IEvent {
    delete this.data[name];
    return this;
  }

  /**
   * Removes the Metric.
   *
   * @param {string} name
   * @returns {IEvent}
   */
  removeMetric(name: string): IEvent {
    delete this.metrics[name];
    return this;
  }

  /**
   * Removes the timer.
   *
   * @param {string} name
   * @returns {IEvent}
   */
  removeTimer(name: string): IEvent {
    delete this.timers[name];
    return this;
  }
}
