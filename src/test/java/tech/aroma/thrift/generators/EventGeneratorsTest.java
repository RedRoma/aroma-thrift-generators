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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.aroma.thrift.events.Event;
import tech.aroma.thrift.events.EventType;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static tech.sirwellington.alchemy.arguments.Arguments.*;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.*;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.*;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;

/**
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class EventGeneratorsTest
{

    @GenerateInteger(value = RANGE, min = 1, max = 50)
    private int amount;

    @Before
    public void setUp() throws Exception
    {
        setupData();
    }

    private void setupData() throws Exception
    {

    }

    @DontRepeat
    @Test
    public void testCannotInstatiate()
    {
        assertThrows(() -> new EventGenerators());
    }

    @Test
    public void testEventTypes()
    {
        AlchemyGenerator<EventType> generator = EventGenerators.eventTypes();

        EventType result = generator.get();
        assertEventType(result);

        List<EventType> eventTypes = listOf(generator, amount);
        assertThat(eventTypes.size(), is(amount));

        eventTypes.forEach(this::assertEventType);
    }

    @Test
    public void testEvents()
    {
        AlchemyGenerator<Event> generator = EventGenerators.events();
        assertThat(generator, notNullValue());

        Event result = generator.get();
        assertEvent(result);

        List<Event> events = listOf(generator, amount);
        assertThat(events.size(), is(amount));
        events.forEach(this::assertEvent);
    }

    private void assertEventType(EventType result)
    {
        assertThat(result, notNullValue());
        assertThat(result.isSet(), is(true));
    }

    private void assertEvent(Event result)
    {
        assertThat(result, notNullValue());

        checkThat(result.eventId)
                .is(nonEmptyString())
                .is(validUUID());

        assertEventType(result.eventType);
    }

}
