import { NativeModules } from 'react-native';
import { EventType } from './constants';
import { ICollectorRegistry, IEvent, IMetricsRecorder } from './types';

/**
 * Class which contains the all the methods exposed through the AppAnalyticsHub
 * native module.
 */
export class AppAnalyticsHub implements IMetricsRecorder, ICollectorRegistry {
  private readonly AppAnalyticsHubNativeModule: any;

  constructor() {
    this.AppAnalyticsHubNativeModule = NativeModules.AppAnalyticsHub;
    if (this.AppAnalyticsHubNativeModule === undefined) {
      console.warn('Please integrate the AppAnalyticsHub native module.');
    }
  }

  /**
   * This method would send the event to the Native ARTN Metric SDK class for it
   * to be recorded in the collectors registered for the event type.
   *
   * @param {IEvent} event
   */
  public record(event: IEvent): void {
    if (this.AppAnalyticsHubNativeModule != null) {
      this.AppAnalyticsHubNativeModule.recordEvent(event.getEventData());
    } else {
      console.warn(
        `Please integrate the AppAnalyticsHub native module for event to be 
        recorded.`
      );
    }
  }

  /**
   * This method would send the given event type and metric collector to the
   * native module for being registered.
   *
   * @param {string | EventType} eventType
   * @param {string} collectorName
   */
  public addCollectorToEventType(
    eventType: string | EventType,
    collectorName: string
  ): void {
    if (this.AppAnalyticsHubNativeModule != null) {
      this.AppAnalyticsHubNativeModule.addCollectorToEventType(
        eventType,
        collectorName
      );
    } else {
      console.warn(
        `Please integrate the AppAnalyticsHub native module to add 
        ${collectorName} collector to ${eventType} event type`
      );
    }
  }

  /**
   * This method would send the given event type and metric collector to the
   * native module for being unregistered.
   *
   * @param {string | EventType} eventType
   * @param {string} collectorName
   */
  public removeCollectorFromEventType(
    eventType: string | EventType,
    collectorName: string
  ): void {
    if (this.AppAnalyticsHubNativeModule != null) {
      this.AppAnalyticsHubNativeModule.removeCollectorFromEventType(
        eventType,
        collectorName
      );
    } else {
      console.warn(
        `Please integrate the AppAnalyticsHub native module to 
        remove ${collectorName} collector from ${eventType} event type`
      );
    }
  }
}

export const appAnalyticsHub = new AppAnalyticsHub();
