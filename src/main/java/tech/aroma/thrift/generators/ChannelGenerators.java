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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aroma.thrift.channels.AndroidDevice;
import tech.aroma.thrift.channels.AromaChannel;
import tech.aroma.thrift.channels.CustomChannel;
import tech.aroma.thrift.channels.Email;
import tech.aroma.thrift.channels.IOSDevice;
import tech.aroma.thrift.channels.MobileDevice;
import tech.aroma.thrift.channels.SlackChannel;
import tech.aroma.thrift.channels.SlackUsername;
import tech.aroma.thrift.channels.WindowsPhoneDevice;
import tech.aroma.thrift.endpoint.Endpoint;
import tech.aroma.thrift.endpoint.HttpThriftEndpoint;
import tech.aroma.thrift.endpoint.TcpEndpoint;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.generator.PeopleGenerators;

import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.BooleanGenerators.booleans;
import static tech.sirwellington.alchemy.generator.NumberGenerators.integers;
import static tech.sirwellington.alchemy.generator.ObjectGenerators.pojos;
import static tech.sirwellington.alchemy.generator.PeopleGenerators.popularEmailDomains;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphabeticString;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphanumericString;
import static tech.sirwellington.alchemy.generator.StringGenerators.hexadecimalString;

/**
 * {@linkplain AlchemyGenerator Alchemy Generators} for {@link AromaChannel} types.
 *
 * @author SirWellington
 */
@NonInstantiable
public final class ChannelGenerators
{
    private final static Logger LOG = LoggerFactory.getLogger(ChannelGenerators.class);

    ChannelGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instatiate");
    }

    public static AlchemyGenerator<SlackChannel> slackChannels()
    {
        return () ->
        {
          return new SlackChannel()
              .setChannelName(one(alphabeticString()))
              .setDomainName(one(PeopleGenerators.popularEmailDomains()))
              .setSlackToken(one(hexadecimalString(10)));
        };
    }

    public static AlchemyGenerator<SlackUsername> slackUsernames()
    {
        return () ->
        {
          return new SlackUsername()
              .setDomainName(one(popularEmailDomains()))
              .setSlackToken(one(hexadecimalString(10)))
              .setUsername(one(alphanumericString()));
        };
    }

    public static AlchemyGenerator<Email> emails()
    {
        return () ->
        {
            return new Email()
                .setEmailAddress(one(PeopleGenerators.emails()))
                .setSubject(one(alphabeticString()));
        };
    }

    public static AlchemyGenerator<Endpoint> endpoints()
    {
        return () ->
        {
            boolean decider = one(booleans());

            Endpoint endpoint = new Endpoint();

            if(decider)
            {
                TcpEndpoint tcp = new TcpEndpoint()
                .setHostname(one(alphanumericString()))
                .setPort(one(integers(80, 8080)));

                endpoint.setTcp(tcp);
            }
            else
            {
                HttpThriftEndpoint http = new HttpThriftEndpoint()
                .setUrl("http://" + one(popularEmailDomains()));

                endpoint.setHttpThrift(http);
            }

            return endpoint;
        };
    }

    public static AlchemyGenerator<CustomChannel> customChannels()
    {
        return () ->
        {
            return new CustomChannel()
                .setEndpoint(one(endpoints()));
        };
    }

    public static AlchemyGenerator<IOSDevice> iosDevices()
    {
        return pojos(IOSDevice.class);
    }

    public static AlchemyGenerator<AndroidDevice> androidDevices()
    {
        return pojos(AndroidDevice.class);
    }
    
    public static AlchemyGenerator<WindowsPhoneDevice> windowsPhoneDevices()
    {
        return () -> new WindowsPhoneDevice();
    }
    
    public static AlchemyGenerator<MobileDevice> mobileDevices()
    {
        AlchemyGenerator<Integer> randomSeeds = integers(1, 4);
        
        return () ->
        {
            MobileDevice device = new MobileDevice();
            
            int seed = randomSeeds.get();
            
            switch (seed)
            {
                case 1:
                    device.setAndroidDevice(one(androidDevices()));
                    break;
                case 2:
                    device.setIosDevice(one(iosDevices()));
                    break;
                case 3:
                    device.setWindowsPhoneDevice(one(windowsPhoneDevices()));
                    break;
                default:
                    device.setIosDevice(one(iosDevices()));
                    break;
            }
            
            return device;
        };
    }
    
    public static AlchemyGenerator<AromaChannel> channels()
    {

        return () ->
        {
            AromaChannel channel = new AromaChannel();

            int number = one(integers(1, 8));

            switch(number)
            {
                case 1:
                    channel.setCustomChannel(one(customChannels()));
                    break;
                case 2:
                    channel.setEmail(one(emails()));
                    break;
                case 3:
                    channel.setSlackChannel(one(slackChannels()));
                    break;
                case 4:
                    channel.setSlackUsername(one(slackUsernames()));
                    break;
                case 5:
                    channel.setIosDevice(one(iosDevices()));
                    break;
                case 6:
                    channel.setAndroidDevice(one(androidDevices()));
                    break;
                default:
                    channel.setSlackChannel(one(slackChannels()));
                    break;
            }

            return channel;
        };

    }

}
