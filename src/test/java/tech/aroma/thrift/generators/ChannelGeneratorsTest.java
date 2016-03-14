/*
 * Copyright 2016 RedRoma.
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
import tech.aroma.thrift.channels.SlackChannel;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;

/**
 *
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class ChannelGeneratorsTest 
{
    
    @GenerateInteger(value = RANGE, min = 5, max = 50)
    private int size;

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
        assertThrows(() -> new ChannelGenerators());
    }
    
    @Test
    public void testSlackChannels()
    {
        AlchemyGenerator<SlackChannel> generator = ChannelGenerators.slackChannels();
        assertThat(generator, notNullValue());
        
        SlackChannel channel = generator.get();
        assertSlackChannel(channel);
        
        List<SlackChannel> channels = listOf(generator, size);
        assertThat(channels.size(), is(size));
        channels.forEach(this::assertSlackChannel);
    }

    @Test
    public void testSlackUsernames()
    {
        ChannelGenerators.slackUsernames();
    }

    @Test
    public void testEmails()
    {
    }

    @Test
    public void testEndpoints()
    {
    }

    @Test
    public void testCustomChannels()
    {
    }

    @Test
    public void testIosDevices()
    {
    }

    @Test
    public void testAndroidDevices()
    {
    }

    @Test
    public void testChannels()
    {
    }

    private void assertSlackChannel(SlackChannel channel)
    {
        assertThat(channel, notNullValue());
        assertThat(channel.channelName, not(isEmptyOrNullString()));
        assertThat(channel.domainName, not(isEmptyOrNullString()));
        assertThat(channel.slackToken, not(isEmptyOrNullString()));
    }

}