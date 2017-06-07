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
import tech.aroma.thrift.authentication.*;
import tech.sirwellington.alchemy.annotations.access.NonInstantiable;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;

import static tech.sirwellington.alchemy.generator.AlchemyGenerator.Get.one;
import static tech.sirwellington.alchemy.generator.EnumGenerators.enumValueOf;
import static tech.sirwellington.alchemy.generator.StringGenerators.alphabeticString;
import static tech.sirwellington.alchemy.generator.StringGenerators.uuids;
import static tech.sirwellington.alchemy.generator.TimeGenerators.futureInstants;

/**
 * {@linkplain AlchemyGenerator Alchemy Generators} for {@link AuthenticationToken}, {@link UserToken}, and
 * {@link ApplicationToken} types.
 *
 * @author SirWellington
 */
@NonInstantiable
public final class TokenGenerators
{

    private final static Logger LOG = LoggerFactory.getLogger(TokenGenerators.class);

    TokenGenerators() throws IllegalAccessException
    {
        throw new IllegalAccessException("cannot instatiate");
    }

    public static AlchemyGenerator<AuthenticationToken> authenticationTokens()
    {
        return () ->
        {
            return new AuthenticationToken()
                    .setTokenId(one(uuids))
                    .setOwnerId(one(uuids))
                    .setOwnerName(one(alphabeticString()))
                    .setOrganizationId(one(uuids))
                    .setOrganizationName(one(alphabeticString()))
                    .setTokenType(enumValueOf(TokenType.class).get())
                    .setStatus(TokenStatus.ACTIVE)
                    .setTimeOfExpiration(futureInstants().get().toEpochMilli());
        };
    }

    public static AlchemyGenerator<ApplicationToken> applicationTokens()
    {
        return () ->
        {
            return new ApplicationToken()
                    .setApplicationId(one(uuids))
                    .setApplicationName(one(alphabeticString()))
                    .setOrganization(one(alphabeticString()))
                    .setTokenId(one(uuids))
                    .setStatus(TokenStatus.ACTIVE)
                    .setTimeOfExpiration(futureInstants().get().toEpochMilli());
        };
    }

    public static AlchemyGenerator<UserToken> userTokens()
    {
        return () ->
        {
            return new UserToken()
                    .setUserId(one(uuids))
                    .setTokenId(one(uuids))
                    .setOrganization(one(alphabeticString()))
                    .setStatus(TokenStatus.ACTIVE)
                    .setTimeOfExpiration(futureInstants().get().toEpochMilli());
        };
    }

}
