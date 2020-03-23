import { Event, EventFactory, EventType, Priority } from '../../src/core';

describe('EventData object must have prototype', () => {
  const eventFactory: EventFactory = new EventFactory({
    source: 'source',
    eventType: EventType.OPERATIONAL
  });
  const event: Event = eventFactory.createEvent({
    name: 'name',
    priority: Priority.HIGH
  });
  it('should have hasOwnProperty method for all data', () => {
    event.incrementCounter('counter');
    event.addData('data', 'customData');
    event.addMetric('metric', true);
    expect(
      event.getEventData().counters.hasOwnProperty('counter')
    ).toBeTruthy();
    expect(event.getEventData().metrics.hasOwnProperty('metric')).toBeTruthy();
    expect(event.getEventData().metrics.metric).toEqual(true);
    expect(event.getEventData().counters.counter).toEqual(1);
    expect(event.getEventData().data.hasOwnProperty('data')).toBeTruthy();
    expect(event.getEventData().data.data).toEqual('customData');
  });

  it('should have eventData with field names and values', () => {
    expect(event.getEventData().hasOwnProperty('eventType')).toBeTruthy();
    expect(event.getEventData().hasOwnProperty('priority')).toBeTruthy();
    expect(event.getEventData().eventType).toEqual(EventType.OPERATIONAL);
    expect(event.getEventData().priority).toEqual('HIGH');
  });
});
