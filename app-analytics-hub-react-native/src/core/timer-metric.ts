import { IEvent, ITimerMetric } from './types';

export class TimerMetric implements ITimerMetric {
  /**
   * Name of the timer.
   */
  name: string;

  /**
   * Parent event for this timer metric.
   */
  parentEvent: IEvent;

  /**
   * start time of the timer
   */
  startTime: number;

  /**
   * Total time that needs to be recorded in the parent event.
   * It would be reset to 0 once it is recorded.
   *
   * Unit is Milliseconds
   */
  totalTime: number;

  /**
   * Constructor of the TimerMetric class.
   *
   * @param {string} name
   * @param {IEvent} parentEvent - optional.
   * @returns {ITimerMetric}
   */
  constructor(name: string, parentEvent?: IEvent) {
    this.name = name;
    this.parentEvent = parentEvent!;
    this.startTime = 0;
    this.totalTime = 0;
  }

  /**
   * Stops and records the timer in the parent event by calling the
   * incrementTimer method.
   * @returns {ITimerMetric}
   */
  recordTimer(): this {
    this.stopTimer();
    if (this.parentEvent) {
      this.parentEvent.removeTimer(this.name);
      this.parentEvent.addTimer(this.name, this.totalTime);
      this.totalTime = 0;
    } else {
      console.error('Could not log this metric as no parent event is defined.');
    }
    return this;
  }

  /**
   * Stops and records the timer in the events that are passed by calling the
   * incrementTimer method.
   * @param {IEvent} events
   */
  recordTimerInEvents(...events: IEvent[]): void {
    this.stopTimer();
    events.forEach((event) => {
      event.removeTimer(this.name);
      event.addTimer(this.name, this.totalTime);
    });
  }

  /**
   * Starts the timer if not already started.
   */
  startTimer(): void {
    if (this.startTime === 0) {
      this.startTime = Date.now();
    }
  }

  /**
   * Stops the timer if started. This method does not record the timer as
   * developer would like to start the timer again.
   *
   * Will change it to end and record if needed.
   * @returns {ITimerMetric}
   */
  stopTimer(): this {
    if (this.startTime > 0) {
      this.totalTime += Date.now() - this.startTime;
      this.startTime = 0;
    } else {
      console.error('Trying to stop a timer without starting it.');
    }
    return this;
  }
}
