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
import tech.aroma.thrift.Message;
import tech.aroma.thrift.Urgency;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;

import static tech.aroma.thrift.application.service.ApplicationServiceConstants.MAX_CHARACTERS_IN_BODY;
import static tech.aroma.thrift.application.service.ApplicationServiceConstants.MAX_TITLE_LENGTH;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.Get.one;
import static tech.sirwellington.alchemy.generator.DateGenerators.pastDates;
import static tech.sirwellington.alchemy.generator.EnumGenerators.enumValueOf;
import static tech.sirwellington.alchemy.generator.StringGenerators.*;

/**
 * @author SirWellington
 */
@NonInstantiable
public final class MessageGenerators
{
    private final static Logger LOG = LoggerFactory.getLogger(MessageGenerators.class);

    MessageGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("Cannot instantiate");
    }

    public static AlchemyGenerator<Message> messages()
    {
        return () ->
        {
            return new Message()
                    .setTitle(one(alphabeticStrings(MAX_TITLE_LENGTH)))
                    .setBody(one(alphabeticStrings(MAX_CHARACTERS_IN_BODY)))
                    .setDeviceName(one(alphabeticStrings(12)))
                    .setHostname(one(alphanumericStrings()))
                    .setUrgency(one(enumValueOf(Urgency.class)))
                    .setTimeMessageReceived(one(pastDates()).getTime())
                    .setApplicationId(one(uuids))
                    .setApplicationName(one(alphabeticStrings(15)))
                    .setMessageId(one(uuids));
        };
    }

}
