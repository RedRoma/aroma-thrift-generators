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
import junit.framework.AssertionFailedError;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.aroma.thrift.channels.AndroidDevice;
import tech.aroma.thrift.channels.AromaChannel;
import tech.aroma.thrift.channels.CustomChannel;
import tech.aroma.thrift.channels.Email;
import tech.aroma.thrift.channels.IOSDevice;
import tech.aroma.thrift.channels.SlackChannel;
import tech.aroma.thrift.channels.SlackUsername;
import tech.aroma.thrift.endpoint.Endpoint;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.PeopleAssertions.validEmailAddress;
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
        AlchemyGenerator<SlackUsername> generator = ChannelGenerators.slackUsernames();
        assertThat(generator, notNullValue());
        
        SlackUsername channel = generator.get();
        assertSlackUsername(channel);
        
        List<SlackUsername> channels = listOf(generator, size);
        assertThat(channels.size(), is(size));
        channels.forEach(this::assertSlackUsername);
    }

    @Test
    public void testEmails()
    {
        AlchemyGenerator<Email> generator = ChannelGenerators.emails();
        assertThat(generator, notNullValue());
        
        Email result = generator.get();
        assertEmail(result);
        
        List<Email> emails = listOf(generator, size);
        assertThat(emails.size(), is(size));
        emails.forEach(this::assertEmail);
    }

    @Test
    public void testEndpoints()
    {
        AlchemyGenerator<Endpoint> generator = ChannelGenerators.endpoints();
        assertThat(generator, notNullValue());
        
        Endpoint result = generator.get();
        assertEndpoint(result);
        
        List<Endpoint> endpoints = listOf(generator, size);
        assertThat(endpoints.size(), is(size));
        endpoints.forEach(this::assertEndpoint);
    }

    @Test
    public void testCustomChannels()
    {
        AlchemyGenerator<CustomChannel> generator = ChannelGenerators.customChannels();
        assertThat(generator, notNullValue());
        
        CustomChannel result = generator.get();
        assertCustomChannel(result);
        
        List<CustomChannel> channels = listOf(generator, size);
        assertThat(channels.size(), is(size));
        channels.forEach(this::assertCustomChannel);
    }

    @Test
    public void testIosDevices()
    {
        AlchemyGenerator<IOSDevice> generator = ChannelGenerators.iosDevices();
        assertThat(generator, notNullValue());
        
        IOSDevice result = generator.get();
        assertIosDevice(result);
        
        List<IOSDevice> devices = listOf(generator, size);
        assertThat(devices.size(), is(size));
        devices.forEach(this::assertIosDevice);
    }

    @Test
    public void testAndroidDevices()
    {
        AlchemyGenerator<AndroidDevice> generator = ChannelGenerators.androidDevices();
        assertThat(generator, notNullValue());
        
        AndroidDevice result = generator.get();
        assertAndroidDevice(result);
        
        List<AndroidDevice> devices = listOf(generator, size);
        assertThat(devices.size(), is(size));
        devices.forEach(this::assertAndroidDevice);
    }

    @Test
    public void testChannels()
    {
        AlchemyGenerator<AromaChannel> generator = ChannelGenerators.channels();
        assertThat(generator, notNullValue());
        
        AromaChannel channel = generator.get();
        assertChannel(channel);
        
        List<AromaChannel> channels = listOf(generator, size);
        assertThat(channels.size(), is(size));
        channels.forEach(this::assertChannel);
    }

    private void assertSlackChannel(SlackChannel channel)
    {
        assertThat(channel, notNullValue());
        assertThat(channel.channelName, not(isEmptyOrNullString()));
        assertThat(channel.domainName, not(isEmptyOrNullString()));
        assertThat(channel.slackToken, not(isEmptyOrNullString()));
    }

    private void assertSlackUsername(SlackUsername channel)
    {
        assertThat(channel, notNullValue());
        assertThat(channel.domainName, not(isEmptyString()));
        assertThat(channel.slackToken, not(isEmptyString()));
        assertThat(channel.username, not(isEmptyString()));
    }

    private void assertEmail(Email email)
    {
        assertThat(email, notNullValue());
        
        checkThat(email.emailAddress)
            .throwing(AssertionFailedError.class)
            .is(validEmailAddress());
    }

    private void assertEndpoint(Endpoint result)
    {
        assertThat(result, notNullValue());
        assertThat(result.isSet(), is(true));
    }

    private void assertCustomChannel(CustomChannel result)
    {
        assertThat(result, notNullValue());
        assertThat(result.endpoint, notNullValue());
        assertThat(result.endpoint.isSet(), is(true));
    }

    private void assertIosDevice(IOSDevice device)
    {
        assertThat(device, notNullValue());
        assertThat(device.deviceToken, not(isEmptyString()));
    }

    private void assertAndroidDevice(AndroidDevice device)
    {
        assertThat(device, notNullValue());
        assertThat(device.deviceId, not(isEmptyString()));
    }

    private void assertChannel(AromaChannel channel)
    {
        assertThat(channel, notNullValue());
        assertThat(channel.isSet(), is(true));
        
        if (channel.isSetAndroidDevice())
        {
            assertAndroidDevice(channel.getAndroidDevice());
        }
        else if (channel.isSetCustomChannel())
        {
            assertCustomChannel(channel.getCustomChannel());
        }
        else if (channel.isSetEmail())
        {
            assertEmail(channel.getEmail());
        }
        else if (channel.isSetIosDevice())
        {
            assertIosDevice(channel.getIosDevice());
        }
        else if (channel.isSetSlackChannel())
        {
            assertSlackChannel(channel.getSlackChannel());
        }
        else if (channel.isSetSlackUsername())
        {
            assertSlackUsername(channel.getSlackUsername());
        }
        else
        {
            fail("AromaChannel is not actually set!: " + channel);
        }
    }

}