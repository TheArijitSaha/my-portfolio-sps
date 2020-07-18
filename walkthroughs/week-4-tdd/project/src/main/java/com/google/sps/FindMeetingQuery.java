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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public final class FindMeetingQuery {
  public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {
    HashSet<TimeRange> busyTimeRanges =
      getBusyTimeRanges(events,
                        request.getAttendees());
    // sort time ranges
    Collection<TimeRange> availableTimeRanges =
      getAvailableTimeRanges(busyTimeRanges,
                             request.getDuration());
    return availableTimeRanges;
  }

  private static HashSet<TimeRange> getBusyTimeRanges(
      Collection<Event> events,
      Collection<String> requiredAttendees) {
    HashSet<TimeRange> busyTimeRanges = new HashSet<TimeRange>();

    for (Event event: events) {
      if (!Collections.disjoint(event.getAttendees(), requiredAttendees)) {
        busyTimeRanges.add(event.getWhen());
      }
    }

    return busyTimeRanges;
  }

  private static Collection<TimeRange> getAvailableTimeRanges(
      HashSet<TimeRange> busyTimeRanges,
      long duration) {
    return busyTimeRanges;
  }
}
