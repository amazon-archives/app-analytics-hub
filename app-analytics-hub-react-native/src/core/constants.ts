/**
 * Event Types that exist in App Analytics Hub.
 */
export enum EventType {
  ENGAGEMENT = 'ENGAGEMENT',
  OPERATIONAL = 'OPERATIONAL'
}

/**
 * Unknown event type to be used for internal purposes.
 */
export const UNKNOWN_TYPE = 'UNKNOWN';

/**
 * This enumeration defines Priority associated with Event when
 * they are being recorded.
 * Priority is used as an indication of time sensitivity of a
 * Event. If the event collector has the functionality of priority then it would
 * be used.
 */
export enum Priority {
  /**
   * Metrics of normal priority will be batched before they are transmitted.
   */
  NORMAL,
  /**
   * Metrics of high priority will be transmitted immediately
   */
  HIGH,
  /**
   * Business critical metrics (e.g. engagement metrics) to be placed in
   * reserved storage space before they are transmitted.
   */
  CRITICAL
}
