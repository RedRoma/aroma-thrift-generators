/*
 * Copyright 2016 RedRoma, Inc.
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
import tech.aroma.thrift.Message;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.aroma.thrift.application.service.ApplicationServiceConstants.MAX_CHARACTERS_IN_BODY;
import static tech.aroma.thrift.application.service.ApplicationServiceConstants.MAX_TITLE_LENGTH;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.stringWithLengthLessThanOrEqualTo;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.validUUID;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;

/**
 *
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class MessageGeneratorsTest
{

    @Before
    public void setUp() throws Exception
    {

    }

    @DontRepeat
    @Test
    public void testConstructor()
    {
        assertThrows(() -> new MessageGenerators());
    }
    
    @Test
    public void testMessages()
    {
        AlchemyGenerator<Message> instance = MessageGenerators.messages();
        assertThat(instance, notNullValue());

        Message message = instance.get();
        testMessage(message);

        List<Message> messages = listOf(instance);
        messages.forEach(this::testMessage);
    }

    private void testMessage(Message message)
    {
        assertThat(message, notNullValue());
        assertThat(message.title, not(isEmptyOrNullString()));
        assertThat(message.body, not(isEmptyOrNullString()));
        assertThat(message.deviceName, not(isEmptyOrNullString()));
        assertThat(message.hostname, not(isEmptyOrNullString()));
        assertThat(message.applicationId, not(isEmptyOrNullString()));
        assertThat(message.messageId, not(isEmptyOrNullString()));

        //Additional checks
        checkThat(message.applicationId).is(validUUID());
        checkThat(message.messageId).is(validUUID());

        checkThat(message.title)
            .is(stringWithLengthLessThanOrEqualTo(MAX_TITLE_LENGTH));

        checkThat(message.body)
            .is(stringWithLengthLessThanOrEqualTo(MAX_CHARACTERS_IN_BODY));
    }

}
