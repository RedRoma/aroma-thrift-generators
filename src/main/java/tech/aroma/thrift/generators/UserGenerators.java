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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.aroma.thrift.User;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;

import static tech.aroma.thrift.generators.ImageGenerators.profileImages;
import static tech.sirwellington.alchemy.generator.AlchemyGenerator.one;
import static tech.sirwellington.alchemy.generator.PeopleGenerators.emails;
import static tech.sirwellington.alchemy.generator.PeopleGenerators.names;
import static tech.sirwellington.alchemy.generator.StringGenerators.uuids;

/**
 * {@linkplain AlchemyGenerator Alchemy Generators} for {@link User} types and Objects.
 *
 * @author SirWellington
 */
@NonInstantiable
public final class UserGenerators
{

    private final static Logger LOG = LoggerFactory.getLogger(UserGenerators.class);

    UserGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instantiate");
    }

    public static AlchemyGenerator<User> usersWithProfileImages()
    {
        return () ->
        {
            return users().get()
                .setProfileImage(one(profileImages()));

        };
    }

    public static AlchemyGenerator<User> users()
    {
        return () ->
        {
            return new User()
                .setEmail(one(emails()))
                .setUserId(one(uuids))
                .setName(one(names()))
                .setFirstName(one(names()))
                .setMiddleName(one(names()))
                .setLastName(one(names()));
        };
    }

}
