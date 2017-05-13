/*
 * Copyright 2017 RedRoma, Inc.
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

package tech.aroma.thrift.generators;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aroma.thrift.events.ApplicationDeleted;
import tech.aroma.thrift.events.ApplicationFollowed;
import tech.aroma.thrift.events.ApplicationSentMessage;
import tech.aroma.thrift.events.ApplicationTokenRegenerated;
import tech.aroma.thrift.events.ApplicationTokenRenewed;
import tech.aroma.thrift.events.Event;
import tech.aroma.thrift.events.EventType;
import tech.aroma.thrift.events.HealthCheckBackToNormal;
import tech.aroma.thrift.events.HealthCheckFailed;
import tech.aroma.thrift.events.OwnerAdded;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.generator.TimeGenerators;

import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.NumberGenerators.integers;
import static tech.sirwellington.alchemy.generator.ObjectGenerators.pojos;
import static tech.sirwellington.alchemy.generator.StringGenerators.uuids;

/**
 * {@linkplain AlchemyGenerator Alchemy Generators} for {@link Event} and {@link EventType}.
 *
 * @author SirWellington
 */
@NonInstantiable
public final class EventGenerators
{

    private final static Logger LOG = LoggerFactory.getLogger(EventGenerators.class);

    EventGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }

    public static AlchemyGenerator<EventType> eventTypes()
    {
        return () ->
        {
            EventType eventType = new EventType();

            int random = one(integers(1, 9));

            switch (random)
            {
                case 1:
                    eventType.setApplicationSentMessage(one(pojos(ApplicationSentMessage.class)));
                    break;
                case 2:
                    eventType.setApplicationTokenRegenerated(one(pojos(ApplicationTokenRegenerated.class)));
                    break;
                case 3:
                    eventType.setApplicationTokenRenewed(one(pojos(ApplicationTokenRenewed.class)));
                    break;
                case 4:
                    eventType.setHealthCheckBackToNormal(one(pojos(HealthCheckBackToNormal.class)));
                    break;
                case 5:
                    eventType.setHealthCheckFailed(one(pojos(HealthCheckFailed.class)));
                    break;
                case 6:
                    eventType.setApplicationDeleted(one(pojos(ApplicationDeleted.class)));
                    break;
                case 7:
                    eventType.setApplicationFollowed(one(pojos(ApplicationFollowed.class)));
                    break;
                case 8:
                    eventType.setOwnerAdded(one(pojos(OwnerAdded.class)));
                    break;
                default:
                    eventType.setApplicationSentMessage(one(pojos(ApplicationSentMessage.class)));
            }

            return eventType;
        };
    }

    public static AlchemyGenerator<Event> events()
    {
        return () ->
        {
            AlchemyGenerator<Instant> pastInstants = TimeGenerators.pastInstants();
            AlchemyGenerator<Long> timestamps = () -> pastInstants.get().toEpochMilli();

            return new Event()
                    .setTimestamp(one(timestamps))
                    .setEventType(one(eventTypes()))
                    .setEventId(one(uuids))
                    .setUserIdOfActor(one(uuids))
                    .setApplicationId(one(uuids));

        };
    }

}
