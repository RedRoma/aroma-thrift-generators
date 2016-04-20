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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aroma.thrift.Urgency;
import tech.aroma.thrift.reactions.AromaMatcher;
import tech.aroma.thrift.reactions.MatcherAll;
import tech.aroma.thrift.reactions.MatcherApplicationIs;
import tech.aroma.thrift.reactions.MatcherApplicationIsNot;
import tech.aroma.thrift.reactions.MatcherBodyContains;
import tech.aroma.thrift.reactions.MatcherBodyDoesNotContain;
import tech.aroma.thrift.reactions.MatcherBodyIs;
import tech.aroma.thrift.reactions.MatcherHostnameContains;
import tech.aroma.thrift.reactions.MatcherHostnameDoesNotContain;
import tech.aroma.thrift.reactions.MatcherHostnameIs;
import tech.aroma.thrift.reactions.MatcherTitleContains;
import tech.aroma.thrift.reactions.MatcherTitleDoesNotContain;
import tech.aroma.thrift.reactions.MatcherTitleIs;
import tech.aroma.thrift.reactions.MatcherTitleIsNot;
import tech.aroma.thrift.reactions.MatcherUrgencyIs;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;

import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.EnumGenerators.enumValueOf;
import static tech.sirwellington.alchemy.generator.NumberGenerators.integers;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphabeticString;
import static tech.sirwellington.alchemy.generator.StringGenerators.uuids;

/**
 *
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
            Urgency urgency = enumValueOf(Urgency.class).get();
            
            switch(random)
            {
                case 0 :
                    matcher.setAll(new MatcherAll());
                    break;
                case 1 :
                    matcher.setApplicationIs(new MatcherApplicationIs(id));;
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
                case 6 :
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
                    matcher.setUrgencyEquals(new MatcherUrgencyIs(urgency));
                    break;
                default :
                    matcher.setAll(new MatcherAll());
                    break;
            }
            
            
            return matcher;
        };
    }

}
