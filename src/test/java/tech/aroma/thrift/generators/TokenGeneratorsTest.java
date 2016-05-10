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

import java.time.Instant;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import tech.aroma.thrift.authentication.ApplicationToken;
import tech.aroma.thrift.authentication.AuthenticationToken;
import tech.aroma.thrift.authentication.UserToken;
import tech.sirwellington.alchemy.generator.AlchemyGenerator;
import tech.sirwellington.alchemy.test.junit.runners.AlchemyTestRunner;
import tech.sirwellington.alchemy.test.junit.runners.DontRepeat;
import tech.sirwellington.alchemy.test.junit.runners.GenerateInteger;
import tech.sirwellington.alchemy.test.junit.runners.Repeat;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static sir.wellington.alchemy.collections.sets.Sets.toSet;
import static tech.sirwellington.alchemy.arguments.Arguments.checkThat;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.nonEmptyString;
import static tech.sirwellington.alchemy.arguments.assertions.StringAssertions.validUUID;
import static tech.sirwellington.alchemy.arguments.assertions.TimeAssertions.inTheFuture;
import static tech.sirwellington.alchemy.generator.CollectionGenerators.listOf;
import static tech.sirwellington.alchemy.test.junit.ThrowableAssertion.assertThrows;
import static tech.sirwellington.alchemy.test.junit.runners.GenerateInteger.Type.RANGE;

/**
 *
 * @author SirWellington
 */
@Repeat(100)
@RunWith(AlchemyTestRunner.class)
public class TokenGeneratorsTest 
{

    @GenerateInteger(value = RANGE, min = 5, max = 50)
    private int size;
    
    @Before
    public void setUp() throws Exception
    {
        
    }

    @DontRepeat
    @Test
    public void testCannotInstatiate()
    {
        assertThrows(() -> new TokenGenerators());
    }
    
    @Test
    public void testAuthenticationTokens()
    {
        AlchemyGenerator<AuthenticationToken> generator = TokenGenerators.authenticationTokens();
        assertThat(generator, notNullValue());
        
        AuthenticationToken token = generator.get();
        assertToken(token);
        
        List<AuthenticationToken> tokens = listOf(generator, size);
        assertThat(tokens.size(), is(size));
        assertThat(toSet(tokens).size(), is(size));
        tokens.forEach(this::assertToken);
    }

    @Test
    public void testApplicationTokens()
    {
        AlchemyGenerator<ApplicationToken> generator = TokenGenerators.applicationTokens();
        assertThat(generator, notNullValue());
        
        ApplicationToken token = generator.get();
        assertToken(token);
        
        List<ApplicationToken> tokens = listOf(generator, size);
        assertThat(toSet(tokens).size(), is(size));
        tokens.forEach(this::assertToken);
    }

    @Test
    public void testUserTokens()
    {
        AlchemyGenerator<UserToken> generator = TokenGenerators.userTokens();
        assertThat(generator, notNullValue());
        
        UserToken token = generator.get();
        assertToken(token);
        
        List<UserToken> tokens = listOf(generator, size);
        assertThat(toSet(tokens).size(), is(size));
        tokens.forEach(this::assertToken);
    }

    private void assertToken(AuthenticationToken token)
    {
        assertThat(token, notNullValue());
        checkThat(token.tokenId, token.ownerId, token.organizationId)
            .are(validUUID());
        
        checkThat(token.ownerName, token.organizationName)
            .are(nonEmptyString());
        
        checkThat(Instant.ofEpochMilli(token.timeOfExpiration))
            .is(inTheFuture());
    }

    private void assertToken(ApplicationToken token)
    {
        assertThat(token, notNullValue());
        
        checkThat(token.tokenId, token.applicationId)
            .are(validUUID());
        
        checkThat(Instant.ofEpochMilli(token.timeOfExpiration))
            .is(inTheFuture());

    }

    private void assertToken(UserToken token)
    {
        assertThat(token, notNullValue());
        
        checkThat(token.tokenId, token.userId)
            .are(validUUID());
        
        checkThat(Instant.ofEpochMilli(token.timeOfExpiration))
            .is(inTheFuture());

    }

}