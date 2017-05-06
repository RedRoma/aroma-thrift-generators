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
import tech.aroma.thrift.Application;
import tech.aroma.thrift.ProgrammingLanguage;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.generator.EnumGenerators;

import static sir.wellington.alchemy.collections.sets.Sets.toSet;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.generator.NumberGenerators.integers;
import static tech.sirwellington.alchemy.generator.NumberGenerators.positiveLongs;
import static tech.sirwellington.alchemy.generator.PeopleGenerators.names;
import static tech.sirwellington.alchemy.generator.StringGenerators.uuids;
import static tech.sirwellington.alchemy.generator.TimeGenerators.pastInstants;

/**
 * {@linkplain AlchemyGenerator Alchemy Generators} for {@link Application} types.
 * 
 * @author SirWellington
 */
@NonInstantiable
public final class ApplicationGenerators
{
    
    private final static Logger LOG = LoggerFactory.getLogger(ApplicationGenerators.class);
    
    ApplicationGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }
    
    public static AlchemyGenerator<Long> pastTimes()
    {
        return () -> pastInstants().get().toEpochMilli();
    }
    
    public static AlchemyGenerator<ProgrammingLanguage> languages()
    {
        return EnumGenerators.enumValueOf(ProgrammingLanguage.class);
    }
    
    public static AlchemyGenerator<Application> applications()
    {
        return () ->
        {
            int numberOfOwners = one(integers(1, 4));
            int numberOfFollowers = one(integers(0, 1000));
            
            return new Application()
                .setApplicationId(one(uuids))
                .setName(names().get())
                .setApplicationIconMediaId(one(uuids))
                .setProgrammingLanguage(one(languages()))
                .setFollowers(toSet(listOf(uuids, numberOfFollowers)))
                .setOwners(toSet(listOf(uuids, numberOfOwners)))
                .setTotalMessagesSent(one(positiveLongs()))
                .setTimeOfProvisioning(one(pastTimes()));
            
        };
    }
    
    public static AlchemyGenerator<Application> applicationsWithIcons()
    {
        return () ->
        {
            Application app = applications().get();
            app.setIcon(one(ImageGenerators.appIcons()));
            
            return app;
        };
    }
}
