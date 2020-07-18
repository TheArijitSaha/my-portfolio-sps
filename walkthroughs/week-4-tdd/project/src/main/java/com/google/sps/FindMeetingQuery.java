// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public final class FindMeetingQuery {

  /**
   * Returns a list of feasible intervals to hold a meeting which matches
   * contraints in given in request and has no overlap with the list of events
   * provided
   *
   * @param events A list of Events registered on a single day
   * @param request A MeetingRequest containing details of the meeting
   */
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    List<TimeRange> busyIntervals =
      getBusyIntervals(events,
                        request.getAttendees());

    Collection<TimeRange> availableIntervals =
      getAvailableIntervals(busyIntervals,
                             request.getDuration());

    return availableIntervals;
  }

  /**
   * Returns a list of intervals in which at least one of the required
   * attendees of the meeting are unavailable
   *
   * @param events A list of Events registered on a single day
   * @param requiredAttendees A list of people required to attend the new meeting
   */
  private static List<TimeRange> getBusyIntervals(
      Collection<Event> events,
      Collection<String> requiredAttendees) {
    // A hashset, so that intervals are unique
    HashSet<TimeRange> busyTimeRangesSet = new HashSet<TimeRange>();

    for (Event event: events) {
      if (!Collections.disjoint(event.getAttendees(), requiredAttendees)) {
        busyTimeRangesSet.add(event.getWhen());
      }
    }

    List<TimeRange> busyIntervals = new ArrayList<TimeRange>(busyTimeRangesSet);

    return busyIntervals;
  }

  /**
   * Returns a list of intervals in which a meeting of required duration
   * can be held where the required attendees are not busy otherwise
   *
   * @param busyIntervals A list of TimeRange each representing an interval where
   *        at least one required attendee is unavailable
   * @param duration An integer representing the duration of the meeting
   */
  private static Collection<TimeRange> getAvailableIntervals(
      List<TimeRange> busyIntervals,
      long duration) {
    // currentOpening stores the minimum minute number of the day where a
    // meeting can be started currently
    int currentOpening = 0;
    List<TimeRange> availableIntervals = new ArrayList<TimeRange>();

    // If duration is non-positive, the meeting can be held any time
    if (duration < 1) {
      availableIntervals.add(TimeRange.WHOLE_DAY);
      return availableIntervals;
    }

    Collections.sort(busyIntervals, TimeRange.ORDER_BY_START);

    for (TimeRange busyInterval: busyIntervals) {
      // If there is an interval big enough to accomodate a meeting of required
      // duration then add the time interval to the list
      if (currentOpening + duration <= busyInterval.start()) {
        availableIntervals.add(
          TimeRange.fromStartEnd(currentOpening, busyInterval.start(), false));
      }

      currentOpening = Math.max(currentOpening, busyInterval.end());
    }

    // If enough time is left at the end of day to accomodate a meeting, add that
    if (currentOpening + duration <= TimeRange.END_OF_DAY) {
      availableIntervals.add(
        TimeRange.fromStartEnd(currentOpening, TimeRange.END_OF_DAY, true));
    }

    return availableIntervals;
  }
}
