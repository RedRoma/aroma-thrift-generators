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
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sir.wellington.alchemy.collections.lists.Lists;
import sir.wellington.alchemy.collections.sets.Sets;
import tech.aroma.thrift.Urgency;
import tech.aroma.thrift.reactions.*;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.generator.NetworkGenerators;

import static tech.sirwellington.alchemy.generator.AlchemyGenerator.Get.one;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.EnumGenerators.enumValueOf;
import static tech.sirwellington.alchemy.generator.NumberGenerators.integers;
import static tech.sirwellington.alchemy.generator.ObjectGenerators.pojos;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphabeticString;
import static tech.sirwellington.alchemy.generator.StringGenerators.uuids;

/**
 * @author SirWellington
 */
@NonInstantiable
public final class ReactionGenerators
{
    private final static Logger LOG = LoggerFactory.getLogger(ReactionGenerators.class);

    ReactionGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("Cannot instantiate");
    }


    public static AlchemyGenerator<AromaMatcher> matchers()
    {
        return () ->
        {
            AromaMatcher matcher = new AromaMatcher();

            int random = one(integers(0, 14));
            String string = one(alphabeticString());
            String id = one(uuids);

            int numberOfUrgencies = one(integers(1, 4));
            AlchemyGenerator<Urgency> urgencyGenerator = enumValueOf(Urgency.class);
            Set<Urgency> urgencies = Sets.toSet(listOf(urgencyGenerator, numberOfUrgencies));

            switch (random)
            {
                case 0:
                    matcher.setAll(new MatcherAll());
                    break;
                case 1:
                    matcher.setApplicationIs(new MatcherApplicationIs(id));
                    break;
                case 2:
                    matcher.setApplicationIsNot(new MatcherApplicationIsNot(id));
                    break;
                case 3:
                    matcher.setBodyContains(new MatcherBodyContains(string));
                    break;
                case 4:
                    matcher.setBodyDoesNotContain(new MatcherBodyDoesNotContain(string));
                    break;
                case 5:
                    matcher.setBodyIs(new MatcherBodyIs(string));
                    break;
                case 6:
                    matcher.setHostnameContains(new MatcherHostnameContains(string));
                    break;
                case 7:
                    matcher.setHostnameDoesNotContain(new MatcherHostnameDoesNotContain(string));
                    break;
                case 8:
                    matcher.setHostnameIs(new MatcherHostnameIs(string));
                    break;
                case 9:
                    matcher.setTitleContains(new MatcherTitleContains(string));
                    break;
                case 10:
                    matcher.setTitleDoesNotContain(new MatcherTitleDoesNotContain(string));
                    break;
                case 11:
                    matcher.setTitleIs(new MatcherTitleIs(string));
                    break;
                case 12:
                    matcher.setTitleIsNot(new MatcherTitleIsNot(id));
                    break;
                case 13:
                    matcher.setUrgencyEquals(new MatcherUrgencyIs(urgencies));
                    break;
                default:
                    matcher.setAll(new MatcherAll());
                    break;
            }


            return matcher;
        };
    }


    public static AlchemyGenerator<AromaAction> actions()
    {
        return () ->
        {

            AromaAction action = new AromaAction();

            String string = one(alphabeticString());
            String url = one(NetworkGenerators.httpUrls()).toString();
            String id = one(uuids);

            int random = one(integers(0, 10));

            switch (random)
            {
                case 0:
                    action.setDontStoreMessage(new ActionDontStoreMessage());
                    break;
                case 1:
                    action.setForwardToSlackChannel(pojos(ActionForwardToSlackChannel.class).get());
                    break;
                case 2:
                    action.setForwardToSlackUser(pojos(ActionForwardToSlackUser.class).get());
                    break;
                case 3:
                    action.setForwardToUsers(new ActionForwardToUsers(Lists.createFrom(id)));
                    break;
                case 4:
                    action.setSkipInbox(new ActionSkipInbox());
                    break;
                case 5:
                    action.setForwardToGitter(new ActionForwardToGitter(url));
                    break;
                default:
                    action.setResponseWithMessage(new ActionRespondWithMessage(string));
                    break;
            }

            return action;
        };
    }

    public static AlchemyGenerator<Reaction> reactions()
    {
        return () ->
        {
            List<AromaMatcher> matchers = listOf(matchers());
            List<AromaAction> actions = listOf(actions());
            String name = one(alphabeticString());

            return new Reaction()
                    .setMatchers(matchers)
                    .setActions(actions)
                    .setName(name);
        };
    }

}
