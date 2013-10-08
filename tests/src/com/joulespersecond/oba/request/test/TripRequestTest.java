/*
 * Copyright (C) 2010 Paul Watts (paulcwatts@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.joulespersecond.oba.request.test;

import com.joulespersecond.oba.elements.ObaRoute;
import com.joulespersecond.oba.request.ObaTripRequest;
import com.joulespersecond.oba.request.ObaTripResponse;
import com.joulespersecond.seattlebusbot.test.UriAssert;

import java.util.HashMap;

@SuppressWarnings("serial")
public class TripRequestTest extends ObaTestCase {

    protected final String TEST_TRIP_ID = "1_18196913";

    // TODO - fix this test in context of regions and loading multiple URLs
    // Currently mixes Tampa URL with KCM data
    public void testKCMTripRequest() {
        ObaTripRequest.Builder builder = new ObaTripRequest.Builder(getContext(), TEST_TRIP_ID);
        ObaTripRequest request = builder.build();
        UriAssert.assertUriMatch(
                "http://api.tampa.onebusaway.org/api/api/where/trip/" + TEST_TRIP_ID + ".json",
                new HashMap<String, String>() {{ put("key", "*"); put("version", "2"); }},
                request);
    }

    public void testKCMTripResponse() throws Exception {
        ObaTripResponse response =
                new ObaTripRequest.Builder(getContext(), TEST_TRIP_ID)
                    .build()
                    .call();
        assertOK(response);
        assertEquals(TEST_TRIP_ID, response.getId());
        assertEquals("1_65", response.getRouteId());

        final ObaRoute route = response.getRoute();
        assertNotNull(route);
        assertEquals("1_65", route.getId());
        assertEquals("1", route.getAgencyId());
    }

    // TODO - fix this test in context of regions and loading multiple URLs
    // Currently mixes Tampa URL with KCM data
    public void testNewRequest() {
        // This is just to make sure we copy and call newRequest() at least once
        ObaTripRequest request = ObaTripRequest.newRequest(getContext(), TEST_TRIP_ID);
        UriAssert.assertUriMatch(
                "http://api.tampa.onebusaway.org/api/api/where/trip/" + TEST_TRIP_ID + ".json",
                new HashMap<String, String>() {{ put("key", "*"); put("version", "2"); }},
                request);
    }
}
