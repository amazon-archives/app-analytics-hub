import { EventType, UNKNOWN_TYPE } from './constants';
import { Event } from './event';
import { IEventFactory, IEventFactoryEventData } from './types';

export class EventFactory implements IEventFactory {
  /**
   * Event type for the events created using this factory. this value can be
   * override by sending a different value while creating the event.
   */
  private eventType?: string | EventType;

  /**
   * Source for the all events created using this factory
   */
  private source?: string;

  /**
   * Creates an Event Factory object for a source.
   * @param options Options to fix in generated events.
   */
  constructor({
    source,
    eventType
  }: {
    /**
     * The source to use for events created by this factory.
     */
    source?: string;
    /**
     * The event type to use for events created by this factory.
     */
    eventType?: string | EventType;
  }) {
    this.source = source;
    this.eventType = eventType!;
  }

  /**
   * Method to create a new Event with the provided source and data.
   * For creating an event, event type is mandatory so either provide the event
   * type in factory constructor or event factory event data.
   *
   * @param {IEventFactoryEventData} eventData
   * @returns {IEvent} would return undefined if event type is not
   * present else would return a new Event instance.
   */
  createEvent(eventData: IEventFactoryEventData): Event {
    let eventType = eventData.eventType ? eventData.eventType : this.eventType;
    if (!eventType) {
      if (__DEV__) {
        throw new Error('No eventType specified.');
      } else {
        eventType = UNKNOWN_TYPE;
      }
    }
    const source = eventData.source ? eventData.source : this.source;
    return new Event(eventData.name, eventType, source, eventData.priority);
  }
}
